package com.routebox.routebox.infrastructure.route_report

import com.routebox.routebox.domain.route_report.RouteReport
import org.springframework.data.jpa.repository.JpaRepository

interface RouteReportRepository : JpaRepository<RouteReport, Long>
