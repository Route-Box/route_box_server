package com.routebox.routebox.domain.route_report

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.route_report.constant.RouteReportReasonType
import com.routebox.routebox.domain.route_report.converter.RouteReportReasonTypesConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "route_report")
@Entity
class RouteReport(
    id: Long = 0,
    reporterId: Long,
    reportedRouteId: Long,
    reasonTypes: List<RouteReportReasonType>?,
    reasonDetail: String?,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_report_id")
    val id: Long = id

    val reporterId = reporterId

    val reportedRouteId = reportedRouteId

    @Convert(converter = RouteReportReasonTypesConverter::class)
    var reasonTypes: List<RouteReportReasonType>? = reasonTypes
        private set

    var reasonDetail: String? = reasonDetail
        private set
}
