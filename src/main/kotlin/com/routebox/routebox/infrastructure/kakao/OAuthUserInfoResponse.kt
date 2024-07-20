package com.routebox.routebox.infrastructure.kakao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OAuthUserInfoResponse(@JsonProperty("id") val uid: String)
