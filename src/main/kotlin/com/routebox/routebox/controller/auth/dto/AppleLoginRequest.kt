package com.routebox.routebox.controller.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class AppleLoginRequest(
    @Schema(
        description = "애플에서 발급받은 id token",
        example = "eyJhbGciOiJSUzI1NiIsImtpZCI6IlQ4dElKMXpTck8ifQ.eyJpc3MiOiJodHRwczovL2FwcGxlLmNvbSIsImlhdCI6MTYwNDAwODQwMCwiZXhwIjoxNjA0MDEyMDAwLCJhdWQiOiJjb20uZXhhbXBsZS5hcHAiLCJzdWIiOiIxMjM0NTY3ODkwIn0.OM9pVzVZXf-TUjs-yyFClwKDO4-IzFUbYAsqZnH2mp-WLV_S7cx8emkVAKVff5Kh1ySm6gnTXC9G0skfgTTXZVV2z0qIOfzU7zB1wCszv9JkN0mB4MWZfgOfVVDLKCRyGFUeXTEeXwC9pF0PAbNPlZT9X5IjBD8ZWlyIvs9bYeIfuILeaBqDla_LsMjaXHl3N8YexuBL6F02csjG1aDsykLyRtT-t07MHYwUsLkWYmTO9tTLydOcUKN6O9-j8gg_0KcMEFZ1v98XtmM1RHwrgebrHPXE7EVOtfFj6jUZFHiU6X7a-36b0i3Ub29hzyWLyLFi9flM1JoTgLkEm3RPmMow",
    )
    @field:NotBlank
    val idToken: String,
)
