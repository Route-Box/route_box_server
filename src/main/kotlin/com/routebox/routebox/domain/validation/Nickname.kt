package com.routebox.routebox.domain.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [NicknameValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Nickname(
    val message: String = "유효하지 않은 닉네임 형식입니다. 닉네임은 특수문자와 공백을 제외한 2~8 글자여야 합니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
