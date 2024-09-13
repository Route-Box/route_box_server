package com.routebox.routebox.domain.route

import com.routebox.routebox.domain.common.FileManager
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.infrastructure.route.RouteActivityImageRepository
import com.routebox.routebox.infrastructure.route.RouteActivityRepository
import com.routebox.routebox.infrastructure.route.RoutePointRepository
import com.routebox.routebox.infrastructure.route.RouteRepository
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Optional
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class RouteServiceTest {
    @InjectMocks
    private lateinit var sut: RouteService

    @Mock
    private lateinit var routeRepository: RouteRepository

    @Mock
    private lateinit var routePointRepository: RoutePointRepository

    @Mock
    private lateinit var routeActivityRepository: RouteActivityRepository

    @Mock
    private lateinit var routeActivityImageRepository: RouteActivityImageRepository

    @Mock
    private lateinit var fileManager: FileManager

    @Test
    fun `루트를 최신순으로 반환한다`() {
        // given
        val id1: Long = Random.nextLong()
        val id2: Long = Random.nextLong()
        val user = createUser()
        val pageable = PageRequest.of(0, 10)
        val route1 = createRoute(id1, user)
        val route2 = createRoute(id2, user)
        val routeList = listOf(route1, route2)
        val userId = Random.nextLong()

        val page: Page<Route> = PageImpl(routeList, pageable, routeList.size.toLong())
        given(routeRepository.findAllFiltered(userId, pageable)).willReturn(page)

        // when
        val actualResult = sut.getLatestRoutes(userId, 0, 10)

        // then
        then(routeRepository).should().findAllFiltered(userId, pageable)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(routeList)
    }

    @Test
    fun `id가 주어지고, 주어진 id로 루트를 조회한다`() {
        // given
        val id = Random.nextLong()
        val expectedResult = createRoute(id, createUser())
        given(routeRepository.findById(id)).willReturn(Optional.of(expectedResult))

        // when
        val actualResult = sut.findRouteById(id)

        // then
        then(routeRepository).should().findById(id)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `유저 id가 주어지고, 주어진 id에 해당하는 유저가 작성한 루트 개수를 조회한다`() {
        // given
        val userId = Random.nextLong()
        val expectedResult = Random.nextInt()
        given(routeRepository.countByUser_Id(userId)).willReturn(expectedResult)

        // when
        val actualResult = sut.countRoutesByUserId(userId)

        // then
        then(routeRepository).should().countByUser_Id(userId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(routeRepository).shouldHaveNoMoreInteractions()
    }

    private fun createUser() = createUser(Random.nextLong())

    private fun createUser(id: Long) = User(
        id = id,
        loginType = LoginType.KAKAO,
        socialLoginUid = RandomStringUtils.random(10),
        nickname = RandomStringUtils.random(5),
        gender = Gender.PRIVATE,
        birthDay = LocalDate.of(2024, 1, 1),
    )

    private fun createRoute(id: Long, user: User) = Route(
        id = id,
        user = user,
        name = RandomStringUtils.random(8, true, true),
        description = RandomStringUtils.random(8, true, true),
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        whoWith = "친구",
        numberOfPeople = 2,
        numberOfDays = "2박3일",
        style = arrayOf("힐링"),
        styles = "",
        transportation = "뚜벅뚜벅",
    )
}
