package com.routebox.routebox.domain.auth

import com.routebox.routebox.domain.user.LoginType
import com.routebox.routebox.exception.kakao.RequestKakaoUserInfoException
import com.routebox.routebox.infrastructure.kakao.KakaoApiClient
import com.routebox.routebox.infrastructure.kakao.OAuthUserInfoResponse
import org.springframework.stereotype.Service

@Service
class AuthService(private val kakaoApiClient: KakaoApiClient) {
    /**
     * OAuth 로그인을 위해, 사용자 정보를 조회한다.
     *
     * 인자로 사용자 정보를 조회하기 위한 auth key를 전달받는다. (각 서비스별 auth key의 의미는 아래 설명 참고)
     *
     * 사용자 정보 조회 후, 사용자를 식별할 수 있는 고유 값(social login uid)을 반환한다. (각 서비스별 social login uid의 의미는 아래 설명 참고)
     *
     * **Kakao** ([사용자 정보 가져오기](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info) 참고)
     * - Auth key: kakao에서 발급받은 access token
     * - Social login uid: `id`(회원번호)
     *
     * **Apple** ([Authenticating users with Sign in with Apple](https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/authenticating_users_with_sign_in_with_apple#3383773) 참고)
     * - Auth key: Apple에서 발급받은 identity token
     * - Social login uid: `sub`
     *
     * @param loginType 사용자 정보를 조회할 플랫폼 서비스 종류
     * @param authKey 사용자 정보를 조회하기 위한 auth key (설명 참고)
     * @return 사용자를 식별할 수 있는 고유 값(social login uid)
     */
    fun getUserInfo(loginType: LoginType, authKey: String): OAuthUserInfoResponse =
        when (loginType) {
            LoginType.KAKAO -> {
                val result = runCatching {
                    kakaoApiClient.getUserInfo("Bearer $authKey")
                }.onFailure { ex ->
                    throw RequestKakaoUserInfoException(ex.message, ex)
                }.getOrThrow()
                result
            }

            LoginType.APPLE -> OAuthUserInfoResponse(uid = "TODO") // TODO: 구현 예정
        }
}
