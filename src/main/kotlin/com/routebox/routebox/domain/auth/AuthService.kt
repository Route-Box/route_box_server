package com.routebox.routebox.domain.auth

import com.routebox.routebox.domain.user.User
import com.routebox.routebox.domain.user.constant.LoginType
import com.routebox.routebox.exception.apple.InvalidAppleIdTokenException
import com.routebox.routebox.exception.apple.RequestAppleAuthKeysException
import com.routebox.routebox.exception.kakao.RequestKakaoUserInfoException
import com.routebox.routebox.infrastructure.apple.AppleApiClient
import com.routebox.routebox.infrastructure.apple.AppleAuthKeys
import com.routebox.routebox.infrastructure.kakao.KakaoApiClient
import com.routebox.routebox.security.JwtInfo
import com.routebox.routebox.security.JwtManager
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

@Service
class AuthService(
    private val kakaoApiClient: KakaoApiClient,
    private val appleApiClient: AppleApiClient,
    private val jwtManager: JwtManager,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
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
    fun getUserInfo(loginType: LoginType, authKey: String): OAuthUserInfo =
        when (loginType) {
            LoginType.KAKAO -> getKakaoUserInfo(kakaoAccessToken = authKey)
            LoginType.APPLE -> getAppleUserInfo(idToken = authKey)
        }

    /**
     * 카카오 사용자 정보 조회
     *
     * @param kakaoAccessToken kakao에서 발급받은 access token
     * @return social login uid(id, 화원번호)
     */
    private fun getKakaoUserInfo(kakaoAccessToken: String): OAuthUserInfo {
        val kakaoUid = runCatching { kakaoApiClient.getUserInfo("Bearer $kakaoAccessToken") }
            .getOrElse { ex -> throw RequestKakaoUserInfoException(ex.message, ex) }
            .id
        return OAuthUserInfo(kakaoUid)
    }

    /**
     * 애플 사용자 정보 조회
     *
     * @param idToken apple에서 발급받은 id token
     * @return social login uid(sub)
     */
    private fun getAppleUserInfo(idToken: String): OAuthUserInfo {
        val validAuthKey = getValidAppleAuthPublicKey(idToken)
        return runCatching {
            val subject = Jwts.parserBuilder()
                .setSigningKey(validAuthKey)
                .build()
                .parseClaimsJws(idToken)
                .body
                .subject
            OAuthUserInfo(subject)
        }.getOrElse { ex -> throw InvalidAppleIdTokenException(ex) }
    }

    /**
     * Apple에서 제공하는 auth keys(public key) 중 idToken에 대해 유효한 auth key를 조회한다.
     *
     * @param idToken apple에서 발급받은 id token
     * @return idToken에 대해 유효한 auth public key
     */
    private fun getValidAppleAuthPublicKey(idToken: String): PublicKey {
        val appleAuthPublicKeys = getAppleAuthPublicKeys()
        val matchedAuthKey = appleAuthPublicKeys.getMatchedAuthKeyByIdToken(idToken)
        val modulus = BigInteger(1, Base64.getUrlDecoder().decode(matchedAuthKey.n))
        val publicExponent = BigInteger(1, Base64.getUrlDecoder().decode(matchedAuthKey.e))
        return KeyFactory.getInstance(matchedAuthKey.kty).generatePublic(RSAPublicKeySpec(modulus, publicExponent))
    }

    /**
     * OAuth 인증 로직에 사용되는 auth keys를 조회한다.
     *
     * @return auth keys(JWT format)
     */
    private fun getAppleAuthPublicKeys(): AppleAuthKeys =
        runCatching { appleApiClient.getAuthKeys() }
            .getOrElse { ex -> throw RequestAppleAuthKeysException(ex.message, ex) }

    /**
     * DB에서 관리하고 있는, 유저가 토큰 재발급에 이용 가능한 refresh token을 조회한다.
     *
     * @param userId id of user
     * @return refresh token
     */
    @Transactional(readOnly = true)
    fun findAvailableRefreshToken(userId: Long): String? =
        refreshTokenRepository.findRefreshToken(userId)?.token

    /**
     * Access token을 발행한다.
     *
     * @param user access token 발행에 필요한 유저 정보가 담긴 user entity
     * @return 발행된 access token 정보 (토큰 값, 만료 시각)
     */
    fun issueAccessToken(user: User): JwtInfo =
        jwtManager.createAccessToken(user.id, user.roles)

    /**
     * Refresh token을 발행한다.
     * 발행된 refresh token은 DB에 저장되어, 추후 토큰 갱신 시 사용한다.
     *
     * @param user refresh token 발행에 필요한 유저 정보가 담긴 user entity
     * @return 발행된 refresh token 정보 (토큰 값, 만료 시각)
     */
    @Transactional
    fun issueRefreshToken(user: User): JwtInfo {
        val refreshToken = jwtManager.createRefreshToken(user.id, user.roles)
        refreshTokenRepository.save(RefreshToken(user.id, refreshToken.token))
        return refreshToken
    }
}

data class OAuthUserInfo(val uid: String)
