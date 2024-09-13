package com.routebox.routebox.domain.auth

enum class WithdrawalReasonType(val description: String) {
    LIMITED_ROUTE_OPTIONS("루트가 다양하지 않아서 유용하지 않음"),
    DIFFICULT_ROUTE_RECORDING("여행 루트를 기록하기 어려움"),
    APP_NOT_AS_EXPECTED("다운로드 시 기대한 내용과 앱이 다름"),
    LOW_SERVICE_TRUST("서비스 운영의 신뢰도가 낮음"),
    ETC("기타"),
    ;

    companion object {
        fun fromString(value: String): WithdrawalReasonType {
            return when (value) {
                "LIMITED_ROUTE_OPTIONS" -> LIMITED_ROUTE_OPTIONS
                "DIFFICULT_ROUTE_RECORDING" -> DIFFICULT_ROUTE_RECORDING
                "APP_NOT_AS_EXPECTED" -> APP_NOT_AS_EXPECTED
                "LOW_SERVICE_TRUST" -> LOW_SERVICE_TRUST
                "ETC" -> ETC
                else -> throw IllegalArgumentException("Invalid value: $value")
            }
        }
    }
}
