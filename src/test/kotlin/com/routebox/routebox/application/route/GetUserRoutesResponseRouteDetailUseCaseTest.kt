package com.routebox.routebox.application.route

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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class GetUserRoutesResponseRouteDetailUseCaseTest {

    @InjectMocks
    lateinit var sut: GetRouteDetailUseCase

    @Mock
    lateinit var routeService: RouteService

    @Test
    fun `id에 해당하는 루트 상세 정보를 반환한다`() {
        // given
        val user = createUser(id = Random.nextLong())
        val routeId = 1L
        val route = createRoute(
            id = routeId,
            name = "Route 1",
            description = "Description 1",
            startTime = LocalDateTime.now(),
            endTime = LocalDateTime.now(),
            whoWith = "Friend",
            numberOfPeople = 2,
            numberOfDays = "3 days",
            style = arrayOf("Hiking"),
            transportation = "Car",
            user = user,
        )
        given(routeService.findRouteById(routeId)).willReturn(route)

        // when
        val actualResult = sut.invoke(routeId)

        // then
        val expectedDtoResult = GetRouteDetailResult.from(route)
        assertThat(actualResult).isEqualTo(expectedDtoResult)
        verify(routeService, times(1)).findRouteById(routeId)
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
    ): Route = Route(
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

    private fun createUser(id: Long): User = User(
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
