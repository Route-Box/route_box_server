package com.routebox.routebox.application.user

import com.routebox.routebox.domain.route.RouteService
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.time.LocalDate
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class GetUserRoutesResponseUserProfileUseCaseTest {

    @InjectMocks
    lateinit var sut: GetUserProfileUseCase

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var routeService: RouteService

    @Test
    fun `유저 프로필 정보를 조회한다`() {
        // given
        val userId = Random.nextLong()
        val expectedUser = createUser(id = userId)
        val expectedNumOfRoutes = Random.nextInt()
        given(userService.getUserById(userId)).willReturn(expectedUser)
        given(routeService.countRoutesByUserId(userId)).willReturn(expectedNumOfRoutes)

        // when
        val actualResult = sut.invoke(userId)

        // then
        then(userService).should().getUserById(userId)
        then(routeService).should().countRoutesByUserId(userId)
        then(userService).shouldHaveNoMoreInteractions()
        then(routeService).shouldHaveNoMoreInteractions()
        assertThat(actualResult)
            .hasFieldOrPropertyWithValue("id", expectedUser.id)
            .hasFieldOrPropertyWithValue("profileImageUrl", expectedUser.profileImageUrl)
            .hasFieldOrPropertyWithValue("nickname", expectedUser.nickname)
            .hasFieldOrPropertyWithValue("gender", expectedUser.gender)
            .hasFieldOrPropertyWithValue("birthDay", expectedUser.birthDay)
            .hasFieldOrPropertyWithValue("introduction", expectedUser.introduction)
            .hasFieldOrPropertyWithValue("numOfRoutes", expectedNumOfRoutes)
            .hasFieldOrProperty("mostVisitedLocation")
            .hasFieldOrProperty("mostTaggedRouteStyles")
    }

    private fun createUser(id: Long) = User(
        id = id,
        loginType = LoginType.KAKAO,
        socialLoginUid = RandomStringUtils.random(10),
        nickname = RandomStringUtils.random(5),
        gender = Gender.PRIVATE,
        birthDay = LocalDate.of(2024, 1, 1),
    )
}
