package com.routebox.routebox.domain.auth

import com.routebox.routebox.domain.user.LoginType
import com.routebox.routebox.exception.kakao.RequestKakaoUserInfoException
import com.routebox.routebox.infrastructure.kakao.KakaoApiClient
import com.routebox.routebox.infrastructure.kakao.OAuthUserInfoResponse
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import kotlin.random.Random
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class OAuthServiceTest {
    @InjectMocks
    lateinit var sut: OAuthService

    @Mock
    lateinit var kakaoApiClient: KakaoApiClient

    @Test
    fun `Kakao에서 발행받은 access token이 주어지고, 주어진 token으로 사용자 정보를 조회한다`() {
        // given
        val kakaoAccessToken = Random.toString()
        val expectedResult = OAuthUserInfoResponse(uid = Random.toString())
        given(kakaoApiClient.getUserInfo(authorizationHeader = "Bearer $kakaoAccessToken")).willReturn(expectedResult)

        // when
        val actualResult = sut.getUserInfo(loginType = LoginType.KAKAO, authKey = kakaoAccessToken)

        // then
        then(kakaoApiClient).should().getUserInfo(authorizationHeader = "Bearer $kakaoAccessToken")
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `Kakao에서 발행받은 access token이 주어지고, 주어진 token으로 사용자 정보를 조회한다, 이때 API에서 오류가 응답된 경우 예외가 발생한다`() {
        // given
        val kakaoAccessToken = Random.toString()
        given(kakaoApiClient.getUserInfo(authorizationHeader = "Bearer $kakaoAccessToken")).willThrow()

        // when
        val ex = catchThrowable { sut.getUserInfo(loginType = LoginType.KAKAO, authKey = kakaoAccessToken) }

        // then
        then(kakaoApiClient).should().getUserInfo(authorizationHeader = "Bearer $kakaoAccessToken")
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(ex).isInstanceOf(RequestKakaoUserInfoException::class.java)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(kakaoApiClient).shouldHaveNoMoreInteractions()
    }
}
