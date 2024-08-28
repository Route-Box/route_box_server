package com.routebox.routebox.application.route

import com.routebox.routebox.application.route.dto.GetLatestRoutesCommand
import com.routebox.routebox.application.route.dto.GetRouteDetailResult
import com.routebox.routebox.domain.route.Route
import com.routebox.routebox.domain.route.RouteService
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class GetUserRoutesResponseLatestRouteUseCaseTest {

    @InjectMocks
    lateinit var sut: GetLatestRoutesUseCase

    @Mock
    lateinit var routeService: RouteService

    @Test
    fun `루트를 최신순으로 반환한다`() {
        // given
        val user = createUser(
            id = Random.nextLong(),
        )
        val route1 = createRoute(
            id = Random.nextLong(),
            name = "Route 1",
            description = "Description 1",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            whoWith = "Friend",
            numberOfPeople = 2,
            numberOfDays = "3 days",
            style = arrayOf("Hiking"),
            transportation = "Car",
            user,
        )
        val route2 = createRoute(
            id = Random.nextLong(),
            name = "Route 2",
            description = "Description 2",
            startTime = LocalDateTime.now().minusDays(1),
            endTime = LocalDateTime.now().minusDays(1),
            whoWith = "Family",
            numberOfPeople = 4,
            numberOfDays = "1 day",
            style = arrayOf("Sightseeing"),
            transportation = "Bus",
            user,
        )
        val page = 0
        val size = 10
        val userId = Random.nextLong()

        val routeList = listOf(route1, route2)
        val expectedResult = routeList.map { GetRouteDetailResult.from(it) }
        given(routeService.getLatestRoutes(userId, page, size)).willReturn(routeList)
        // when
        val actualResult = sut.invoke(GetLatestRoutesCommand(userId, page, size))

        // then
        then(routeService).should().getLatestRoutes(userId, page, size)
        then(routeService).shouldHaveNoMoreInteractions()

        assertThat(actualResult).isEqualTo(expectedResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(routeService).shouldHaveNoMoreInteractions()
    }

    private fun createRoute(
        id: Long,
        name: String,
        description: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        whoWith: String,
        numberOfPeople: Int,
        numberOfDays: String,
        style: Array<String>,
        transportation: String,
        user: User,
    ) = Route(
        id = id,
        name = name,
        description = description,
        startTime = startTime,
        endTime = endTime,
        whoWith = whoWith,
        numberOfPeople = numberOfPeople,
        numberOfDays = numberOfDays,
        style = style,
        transportations = transportation,
        user = user,
    )

    private fun createUser(
        id: Long,
    ) = User(
        id = id,
        loginType = LoginType.KAKAO,
        socialLoginUid = Random.nextInt().toString(),
        nickname = Random.nextLong().toString(),
        gender = Gender.FEMALE,
        birthDay = LocalDate.now(),
        introduction = Random.nextLong().toString(),
        profileImageUrl = Random.nextLong().toString(),
    )
}
