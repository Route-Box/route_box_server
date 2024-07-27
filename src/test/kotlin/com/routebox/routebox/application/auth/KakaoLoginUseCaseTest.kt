package com.routebox.routebox.application.auth

import com.routebox.routebox.application.auth.dto.KakaoLoginCommand
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
class KakaoLoginUseCaseTest {
    @InjectMocks
    lateinit var sut: KakaoLoginUseCase

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var authService: AuthService

    @Mock
    lateinit var jwtManager: JwtManager

    @Test
    fun `(신규 유저) Kakao에서 발행한 access token이 주어지고, 주어진 token으로 유저 정보 조회 및 회원가입을 진행한다`() {
        // given
        val kakaoAccessToken = Random.toString()
        val kakaoUid = Random.toString()
        val newUser = createUser(id = Random.nextLong())
        val expectedAccessTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())
        val expectedRefreshTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())
        given(authService.getUserInfo(LoginType.KAKAO, kakaoAccessToken))
            .willReturn(OAuthUserInfo(uid = kakaoUid))
        given(userService.createNewUser(LoginType.KAKAO, kakaoUid)).willReturn(newUser)
        given(authService.issueAccessToken(newUser)).willReturn(expectedAccessTokenResult)
        given(authService.issueRefreshToken(newUser)).willReturn(expectedRefreshTokenResult)

        // when
        val result = sut.invoke(KakaoLoginCommand(kakaoAccessToken))

        // then
        then(authService).should().getUserInfo(LoginType.KAKAO, kakaoAccessToken)
        then(userService).should().createNewUser(LoginType.KAKAO, kakaoUid)
        then(authService).should().issueAccessToken(newUser)
        then(authService).should().issueRefreshToken(newUser)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.isNew).isTrue()
        assertThat(result.accessToken).isEqualTo(expectedAccessTokenResult)
        assertThat(result.refreshToken).isEqualTo(expectedRefreshTokenResult)
    }

    @Test
    fun `(기존 유저) Kakao에서 발행한 access token이 주어지고, 주어진 token으로 유저 정보 조회 및 로그인을 진행한다`() {
        // given
        val kakaoAccessToken = Random.toString()
        val kakaoUid = Random.toString()
        val user = createUser(id = Random.nextLong())
        val expectedAccessTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())
        val expectedRefreshTokenResult = JwtInfo(token = Random.toString(), expiresAt = LocalDateTime.now())

        // 기존 유저 데이터를 표현하기 위해 createdAt != updatedAt이 되게끔 set.
        ReflectionTestUtils.setField(user, "updatedAt", LocalDateTime.now().plusDays(1))

        given(authService.getUserInfo(LoginType.KAKAO, kakaoAccessToken))
            .willReturn(OAuthUserInfo(uid = kakaoUid))
        given(userService.createNewUser(LoginType.KAKAO, kakaoUid))
            .willThrow(UserSocialLoginUidDuplicationException::class.java)
        given(userService.getUserBySocialLoginUid(kakaoUid)).willReturn(user)
        given(authService.issueAccessToken(user)).willReturn(expectedAccessTokenResult)
        given(authService.issueRefreshToken(user)).willReturn(expectedRefreshTokenResult)

        // when
        val result = sut.invoke(KakaoLoginCommand(kakaoAccessToken))

        // then
        then(authService).should().getUserInfo(LoginType.KAKAO, kakaoAccessToken)
        then(userService).should().createNewUser(LoginType.KAKAO, kakaoUid)
        then(userService).should().getUserBySocialLoginUid(kakaoUid)
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
        loginType = LoginType.KAKAO,
        socialLoginUid = Random.toString(),
        nickname = Random.toString(),
        gender = Gender.PRIVATE,
        birthDay = LocalDate.of(2024, 1, 1),
    )
}
