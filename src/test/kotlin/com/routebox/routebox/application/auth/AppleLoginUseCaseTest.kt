package com.routebox.routebox.application.auth

import com.routebox.routebox.application.auth.dto.AppleLoginCommand
import com.routebox.routebox.domain.auth.AuthService
import com.routebox.routebox.domain.auth.OAuthUserInfo
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.domain.user.constant.Gender
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.security.JwtInfo
import com.routebox.routebox.security.JwtManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class AppleLoginUseCaseTest {
    @InjectMocks
    lateinit var sut: AppleLoginUseCase

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var authService: AuthService

    @Mock
    lateinit var jwtManager: JwtManager

    @Test
    fun `(신규 유저) Apple에서 발행한 id token이 주어지고, 주어진 token으로 유저 정보 조회 및 회원가입을 진행한다`() {
        // given
        val appleIdToken = Random.toString()
        val appleUid = Random.toString()
        val newUser = createUser(id = Random.nextLong())
        val expectedAccessTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())
        val expectedRefreshTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())
        given(authService.getUserInfo(LoginType.APPLE, appleIdToken))
            .willReturn(OAuthUserInfo(uid = appleUid))
        given(userService.createNewUser(LoginType.APPLE, appleUid)).willReturn(newUser)
        given(authService.issueAccessToken(newUser)).willReturn(expectedAccessTokenResult)
        given(authService.issueRefreshToken(newUser)).willReturn(expectedRefreshTokenResult)

        // when
        val result = sut.invoke(AppleLoginCommand(appleIdToken))

        // then
        then(authService).should().getUserInfo(LoginType.APPLE, appleIdToken)
        then(userService).should().createNewUser(LoginType.APPLE, appleUid)
        then(authService).should().issueAccessToken(newUser)
        then(authService).should().issueRefreshToken(newUser)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.isNew).isTrue()
        assertThat(result.accessToken).isEqualTo(expectedAccessTokenResult)
        assertThat(result.refreshToken).isEqualTo(expectedRefreshTokenResult)
    }

    @Test
    fun `(기존 유저) Apple에서 발행한 id token이 주어지고, 주어진 token으로 유저 정보 조회 및 로그인을 진행한다`() {
        // given
        val appleIdToken = Random.toString()
        val appleUid = Random.toString()
        val user = createUser(id = Random.nextLong())
        val expectedAccessTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())
        val expectedRefreshTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())

        // 기존 유저 데이터를 표현하기 위해 createdAt != updatedAt이 되게끔 set.
        ReflectionTestUtils.setField(user, "updatedAt", LocalDateTime.now().plusDays(1))

        given(authService.getUserInfo(LoginType.APPLE, appleIdToken))
            .willReturn(OAuthUserInfo(uid = appleUid))
        given(userService.createNewUser(LoginType.APPLE, appleUid))
            .willThrow(UserSocialLoginUidDuplicationException::class.java)
        given(userService.getUserBySocialLoginUid(appleUid)).willReturn(user)
        given(authService.issueAccessToken(user)).willReturn(expectedAccessTokenResult)
        given(authService.issueRefreshToken(user)).willReturn(expectedRefreshTokenResult)

        // when
        val result = sut.invoke(AppleLoginCommand(appleIdToken))

        // then
        then(authService).should().getUserInfo(LoginType.APPLE, appleIdToken)
        then(userService).should().createNewUser(LoginType.APPLE, appleUid)
        then(userService).should().getUserBySocialLoginUid(appleUid)
        then(authService).should().issueAccessToken(user)
        then(authService).should().issueRefreshToken(user)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.isNew).isFalse()
        assertThat(result.accessToken).isEqualTo(expectedAccessTokenResult)
        assertThat(result.refreshToken).isEqualTo(expectedRefreshTokenResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(userService).shouldHaveNoMoreInteractions()
        then(authService).shouldHaveNoMoreInteractions()
        then(jwtManager).shouldHaveNoMoreInteractions()
    }

    private fun createUser(id: Long) = User(
        id = id,
        loginType = LoginType.APPLE,
        socialLoginUid = Random.toString(),
        nickname = Random.toString(),
        gender = Gender.PRIVATE,
        birthDay = LocalDate.of(2024, 1, 1),
    )
}
