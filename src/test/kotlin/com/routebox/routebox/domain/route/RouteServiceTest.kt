package com.routebox.routebox.domain.route

import com.routebox.routebox.infrastructure.route.RoutePointRepository
import com.routebox.routebox.infrastructure.route.RouteRepository
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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

    @Test
    fun `루트를 최신순으로 반환한다`() {
        // given
        val id1: Long = Random.nextLong()
        val id2: Long = Random.nextLong()
        val pageable = PageRequest.of(0, 10)
        val route1 = createRoute(id1)
        val route2 = createRoute(id2)
        val routeList = listOf(route1, route2)

        val page: Page<Route> = PageImpl(routeList, pageable, routeList.size.toLong())
        given(routeRepository.findAllByOrderByCreatedAtDesc(pageable)).willReturn(page)

        // when
        val actualResult = sut.getLatestRoutes(0, 10)

        // then
        then(routeRepository).should().findAllByOrderByCreatedAtDesc(pageable)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        Assertions.assertThat(actualResult).isEqualTo(routeList)
    }

    @Test
    fun `id가 주어지고, 주어진 id로 루트를 조회한다`() {
        // given
        val id = Random.nextLong()
        val expectedResult = createRoute(id)
        given(routeRepository.findById(id)).willReturn(Optional.of(expectedResult))

        // when
        val actualResult = sut.getRouteById(id)

        // then
        then(routeRepository).should().findById(id)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        Assertions.assertThat(actualResult).isEqualTo(expectedResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(routeRepository).shouldHaveNoMoreInteractions()
    }

    private fun createRoute(id: Long) = Route(
        id = id,
        name = RandomStringUtils.random(8, true, true),
        description = RandomStringUtils.random(8, true, true),
        startTime = LocalDateTime.now(),
        endTime = LocalDateTime.now(),
        whoWith = "친구",
        numberOfPeople = 2,
        numberOfDays = "2박3일",
        style = arrayOf("힐링"),
        transportation = arrayOf("뚜벅뚜벅"),
    )
}
