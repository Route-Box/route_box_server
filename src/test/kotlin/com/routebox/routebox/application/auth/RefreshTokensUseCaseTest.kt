package com.routebox.routebox.application.auth

import com.routebox.routebox.domain.auth.AuthService
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.security.InvalidTokenException
import com.routebox.routebox.security.JwtInfo
import com.routebox.routebox.security.JwtManager
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
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
class RefreshTokensUseCaseTest {
    @InjectMocks
    private lateinit var sut: RefreshTokensUseCase

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var authService: AuthService

    @Mock
    private lateinit var jwtManager: JwtManager

    @Test
    fun `refresh token이 주어지고, 주어진 refresh token으로 새로운 access token과 refresh token을 발급한다`() {
        // given
        val refreshToken = RandomStringUtils.random(30)
        val userId = Random.nextLong()
        val user = createUser(userId)
        val expectedAccessTokenResult = JwtInfo(token = RandomStringUtils.random(10), expiresAt = LocalDateTime.now())
        val expectedRefreshTokenResult = JwtInfo(token = RandomStringUtils.random(10), expiresAt = LocalDateTime.now())
        given(jwtManager.getUserIdFromToken(refreshToken)).willReturn(userId)
        given(authService.findAvailableRefreshToken(userId)).willReturn(refreshToken)
        given(userService.getUserById(userId)).willReturn(user)
        given(authService.issueAccessToken(user)).willReturn(expectedAccessTokenResult)
        given(authService.issueRefreshToken(user)).willReturn(expectedRefreshTokenResult)

        // when
        val (actualAccessTokenResult, actualRefreshTokenResult) = sut.invoke(refreshToken)

        // then
        then(jwtManager).should().getUserIdFromToken(refreshToken)
        then(authService).should().findAvailableRefreshToken(userId)
        then(userService).should().getUserById(userId)
        then(authService).should().issueAccessToken(user)
        then(authService).should().issueRefreshToken(user)
        then(userService).shouldHaveNoMoreInteractions()
        then(authService).shouldHaveNoMoreInteractions()
        then(jwtManager).shouldHaveNoMoreInteractions()
        assertThat(actualAccessTokenResult).isEqualTo(expectedAccessTokenResult)
        assertThat(actualRefreshTokenResult).isEqualTo(expectedRefreshTokenResult)
    }

    @Test
    fun `refresh token이 주어지고, 주어진 refresh token으로 새로운 access token과 refresh token을 발급한다, 만약 이용 가능한 refresh token이 없다면 예외가 발생한다`() {
        // given
        val refreshToken = RandomStringUtils.random(30)
        val userId = Random.nextLong()
        given(jwtManager.getUserIdFromToken(refreshToken)).willReturn(userId)
        given(authService.findAvailableRefreshToken(userId)).willReturn(null)

        // when
        val ex = catchThrowable { sut.invoke(refreshToken) }

        // then
        then(jwtManager).should().getUserIdFromToken(refreshToken)
        then(authService).should().findAvailableRefreshToken(userId)
        then(userService).shouldHaveNoInteractions()
        then(authService).shouldHaveNoMoreInteractions()
        then(jwtManager).shouldHaveNoMoreInteractions()
        assertThat(ex).isInstanceOf(InvalidTokenException::class.java)
    }

    @Test
    fun `refresh token이 주어지고, 주어진 refresh token으로 새로운 access token과 refresh token을 발급한다, 만약 주어진 refresh token과 이용 가능한 refresh token이 다르다면 예외가 발생한다`() {
        // given
        val refreshToken = RandomStringUtils.random(30)
        val availableRefreshToken = RandomStringUtils.random(40)
        val userId = Random.nextLong()
        given(jwtManager.getUserIdFromToken(refreshToken)).willReturn(userId)
        given(authService.findAvailableRefreshToken(userId)).willReturn(availableRefreshToken)

        // when
        val ex = catchThrowable { sut.invoke(refreshToken) }

        // then
        then(jwtManager).should().getUserIdFromToken(refreshToken)
        then(authService).should().findAvailableRefreshToken(userId)
        then(userService).shouldHaveNoInteractions()
        then(authService).shouldHaveNoMoreInteractions()
        then(jwtManager).shouldHaveNoMoreInteractions()
        assertThat(ex).isInstanceOf(InvalidTokenException::class.java)
    }

    private fun createUser(userId: Long): User =
        User(
            id = userId,
            loginType = LoginType.KAKAO,
            socialLoginUid = RandomStringUtils.random(10),
            nickname = RandomStringUtils.random(5),
            gender = Gender.PRIVATE,
            birthDay = LocalDate.of(2024, 1, 1),
        )
}
