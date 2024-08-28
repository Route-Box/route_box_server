package com.routebox.routebox.domain.route_report.constant

enum class RouteReportReasonType(val description: String) {
    IRRELEVANT_CONTENT("여행 내용과 관련 없는 내용임"),
    ADVERTISEMENT("광고/홍보성 게시글임"),
    INAPPROPRIATE_OR_HATEFUL_CONTENT("선정적이거나 폭력, 혐오적임"),
    COPYRIGHT_INFRINGEMENT_OR_IMPERSONATION("무단 도용, 사칭, 저작권 침해가 의심됨"),
    PRIVACY_VIOLATION("개인 정보 노출이 우려됨"),
    ETC("기타"),
}
