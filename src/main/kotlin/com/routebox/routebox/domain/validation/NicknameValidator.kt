package com.routebox.routebox.domain.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NicknameValidator : ConstraintValidator<Nickname, String> {
    override fun isValid(nickname: String?, contextt: ConstraintValidatorContext): Boolean {
        if (nickname.isNullOrEmpty()) {
            return true
        }
        val nicknamePattern = "^[a-zA-Z가-힣ㄱ-ㅎ0-9]{2,8}$".toRegex()
        return nickname.matches(nicknamePattern)
    }
}
