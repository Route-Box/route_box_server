package com.routebox.routebox.application.auth

import com.routebox.routebox.domain.auth.OAuthService
import com.routebox.routebox.domain.user.Gender
import com.routebox.routebox.domain.user.LoginType
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.UserService
import com.routebox.routebox.exception.user.UserSocialLoginUidDuplicationException
import com.routebox.routebox.infrastructure.kakao.OAuthUserInfoResponse
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
    lateinit var oAuthService: OAuthService

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
        given(oAuthService.getUserInfo(LoginType.KAKAO, kakaoAccessToken))
            .willReturn(OAuthUserInfoResponse(uid = kakaoUid))
        given(userService.createNewUser(LoginType.KAKAO, kakaoUid)).willReturn(newUser)
        given(jwtManager.createAccessToken(newUser.id, newUser.roles)).willReturn(expectedAccessTokenResult)
        given(jwtManager.createRefreshToken(newUser.id, newUser.roles)).willReturn(expectedRefreshTokenResult)

        // when
        val result = sut.invoke(KakaoLoginCommand(kakaoAccessToken))

        // then
        then(oAuthService).should().getUserInfo(LoginType.KAKAO, kakaoAccessToken)
        then(userService).should().createNewUser(LoginType.KAKAO, kakaoUid)
        then(jwtManager).should().createAccessToken(newUser.id, newUser.roles)
        then(jwtManager).should().createRefreshToken(newUser.id, newUser.roles)
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

        given(oAuthService.getUserInfo(LoginType.KAKAO, kakaoAccessToken))
            .willReturn(OAuthUserInfoResponse(uid = kakaoUid))
        given(userService.createNewUser(LoginType.KAKAO, kakaoUid))
            .willThrow(UserSocialLoginUidDuplicationException::class.java)
        given(userService.getUserBySocialLoginUid(kakaoUid)).willReturn(user)
        given(jwtManager.createAccessToken(user.id, user.roles)).willReturn(expectedAccessTokenResult)
        given(jwtManager.createRefreshToken(user.id, user.roles)).willReturn(expectedRefreshTokenResult)

        // when
        val result = sut.invoke(KakaoLoginCommand(kakaoAccessToken))

        // then
        then(oAuthService).should().getUserInfo(LoginType.KAKAO, kakaoAccessToken)
        then(userService).should().createNewUser(LoginType.KAKAO, kakaoUid)
        then(userService).should().getUserBySocialLoginUid(kakaoUid)
        then(jwtManager).should().createAccessToken(user.id, user.roles)
        then(jwtManager).should().createRefreshToken(user.id, user.roles)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(result.isNew).isFalse()
        assertThat(result.accessToken).isEqualTo(expectedAccessTokenResult)
        assertThat(result.refreshToken).isEqualTo(expectedRefreshTokenResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(userService).shouldHaveNoMoreInteractions()
        then(oAuthService).shouldHaveNoMoreInteractions()
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