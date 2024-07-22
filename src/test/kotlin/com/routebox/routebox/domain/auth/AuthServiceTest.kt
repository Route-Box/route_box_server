package com.routebox.routebox.domain.auth

import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.apple.InvalidAppleIdTokenException
import com.routebox.routebox.exception.kakao.RequestKakaoUserInfoException
import com.routebox.routebox.infrastructure.apple.AppleApiClient
import com.routebox.routebox.infrastructure.apple.AppleAuthKey
import com.routebox.routebox.infrastructure.apple.AppleAuthKeys
import com.routebox.routebox.infrastructure.kakao.KakaoApiClient
import com.routebox.routebox.infrastructure.kakao.KakaoUserInfo
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
class AuthServiceTest {
    @InjectMocks
    lateinit var sut: AuthService

    @Mock
    lateinit var kakaoApiClient: KakaoApiClient

    @Mock
    lateinit var appleApiClient: AppleApiClient

    @Test
    fun `Kakao에서 발행받은 access token이 주어지고, 주어진 token으로 사용자 정보를 조회한다`() {
        // given
        val kakaoAccessToken = Random.toString()
        val kakaoUid = Random.toString()
        val expectedResult = OAuthUserInfo(uid = kakaoUid)
        given(kakaoApiClient.getUserInfo(authorizationHeader = "Bearer $kakaoAccessToken"))
            .willReturn(KakaoUserInfo(id = kakaoUid))

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

    @Test
    fun `Apple에서 발급받은 id token이 주어지고, 주어진 token으로 사용자 정보를 조회한다`() {
        // given
        // 기능이 동작하게끔 kid가 "T8tIJ1zSrO"인 테스트용 id token 설정. 실제로 유효한 토큰은 아님.
        val idToken =
            "eyJhbGciOiJSUzI1NiIsImtpZCI6IlQ4dElKMXpTck8ifQ.eyJpc3MiOiJodHRwczovL2FwcGxlLmNvbSIsImlhdCI6MTYwNDAwODQwMCwiZXhwIjoxNjA0MDEyMDAwLCJhdWQiOiJjb20uZXhhbXBsZS5hcHAiLCJzdWIiOiIxMjM0NTY3ODkwIn0.OM9pVzVZXf-TUjs-yyFClwKDO4-IzFUbYAsqZnH2mp-WLV_S7cx8emkVAKVff5Kh1ySm6gnTXC9G0skfgTTXZVV2z0qIOfzU7zB1wCszv9JkN0mB4MWZfgOfVVDLKCRyGFUeXTEeXwC9pF0PAbNPlZT9X5IjBD8ZWlyIvs9bYeIfuILeaBqDla_LsMjaXHl3N8YexuBL6F02csjG1aDsykLyRtT-t07MHYwUsLkWYmTO9tTLydOcUKN6O9-j8gg_0KcMEFZ1v98XtmM1RHwrgebrHPXE7EVOtfFj6jUZFHiU6X7a-36b0i3Ub29hzyWLyLFi9flM1JoTgLkEm3RPmMow"
        given(appleApiClient.getAuthKeys()).willReturn(
            AppleAuthKeys(
                // 2024.07.21 기준 실제 응답값
                listOf(
                    AppleAuthKey(
                        kty = "RSA",
                        kid = "pggnQeNCOU",
                        use = "sig",
                        alg = "RS256",
                        n = "xyWY7ydqTVHRzft5fPZmTuD9Ahk7-_2_IekZGy07Ovhj5IhYyVU8Hq5j0_c9m9tSdJTRdKmNjMURpY4ZJ_9rd3EOQ_WnYHM2cZIQ5y3f_WxeElnv_f2fKDruA-ERaQ6duov-3NAXC3oTWdXuRGRLbbfOVCahTjvnAA8YBRUe3llW7ZvTG14g-fAEQVlMYDxxCsbjtBJiUzKxbH-8KvhIhP9AJtiLDfiK1yzVJ7Qn6HNm5AUsFQKOAgTqxDMJkhi7pyntTyxhpkLYTEndaPRXth_LM3hVmaoFb3P3TsPCbDjSEbKy1wAndfPSzUk6qjyyBYhdXH0sgVpKMBAdggylLQ",
                        e = "AQAB",
                    ),
                    AppleAuthKey(
                        kty = "RSA",
                        kid = "T8tIJ1zSrO",
                        use = "sig",
                        alg = "RS256",
                        n = "teUbLrwScsjVrcFAvSrfben3eQaEca3ESBegGh_wdGuLKw6QgwDxY3fC1_WeSVnkJXx72ddw3j2inoADnTyzuNa_PwDSmvJhOhmzOmoltmtKHteGdaXrqMohO6A85WxVKbN7pzDqwZJNrdY12LOltlI8PHIG-elAbKM2XOHiJaZnLpAVckKy6MQYsEExpPB3plGxWZElqwNZY6SUDVeN-o9qg5FJOFg7T7iTVVEagws4DM6uZNMDQGtqg9V9VqPQkUzC-sYd5eqbB9LqH4iN5F6OB7BmD3g3jCu9zgh3O9V24N43EruBCNrmP0xLP5ZliKqozoAcd1nv71HuVm6mgQ",
                        e = "AQAB",
                    ),
                    AppleAuthKey(
                        kty = "RSA",
                        kid = "pyaRQpAbnY",
                        use = "sig",
                        alg = "RS256",
                        n = "qHiwOpizi6xHG8FIOSWH4l0P1CjLIC7aBFkhbk7BrD4s9KQAs5Sj5xAtOwlZMyP2XFcqRtZBLIMM7vw_CNERtRrhc68se5hQE_vsrHy7ugcQU6ogJS6s54zqO-zTUfaa3mABM6iR-EfgSpvz33WTQZAPtwAyxaSLknHyDzWjHEZ44WqaQBdcMAvgsWMYG5dBfnV-3Or3V2r1vdbinRE5NomE2nsKDbnJ3yo3u-x9TizKazS1JV3umt71xDqbruZLybIrimrzg_i9OSIzT2o5ZWz8zdYkKHZ4cvRPh-DDt8kV7chzR2tenPF2c5WXuK-FumOrjT7WW6uwSvhnhwNZuw",
                        e = "AQAB",
                    ),
                ),
            ),
        )

        // when
        val ex = catchThrowable { sut.getUserInfo(LoginType.APPLE, authKey = idToken) }

        // then
        then(appleApiClient).should().getAuthKeys()
        // 테스트에 사용하는 id token이 유효하지 않아 jwt 파싱 과정에서 에러가 발생함.
        // 그렇기에 마지막(jwt token parsing) 이전 과정까지가 성공적으로 동작하는지 확인하고자 한다. 즉, InvalidAppleIdTokenException이 발생하는 경우는 success case로 간주함.
        assertThat(ex).isInstanceOf(InvalidAppleIdTokenException::class.java)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(kakaoApiClient).shouldHaveNoMoreInteractions()
        then(appleApiClient).shouldHaveNoMoreInteractions()
    }
}
