package com.routebox.routebox.application.auth.dto

import com.routebox.routebox.domain.user.constant.LoginType

/**
 * `token`
 * - Kakao: access token
 * - Apple: identity token
 */
data class OAuthLoginCommand(
    val loginType: LoginType,
    val token: String,
)
