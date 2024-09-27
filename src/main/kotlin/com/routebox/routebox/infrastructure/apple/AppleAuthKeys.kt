package com.routebox.routebox.infrastructure.apple

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.Base64

data class AppleAuthKeys(val keys: List<AppleAuthKey>) {
    fun getMatchedAuthKeyByIdToken(idToken: String): AppleAuthKey {
        val mapper = ObjectMapper()
        val headersOfIdToken = mapper.readValue(
            Base64.getDecoder().decode(idToken.substringBefore(".")),
            object : TypeReference<Map<String, Any>>() {},
        )
        return this.keys.first { key -> key.kid == headersOfIdToken["kid"] && key.alg == headersOfIdToken["alg"] }
    }
}

data class AppleAuthKey(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String,
)
