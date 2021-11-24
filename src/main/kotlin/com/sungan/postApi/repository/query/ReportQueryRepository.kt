package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.report.Report
import java.time.LocalDateTime

interface ReportQueryRepository {
    fun findReportsAfterLastHotplacePagingOrderByCreatedAtDesc(
        size: Long,
        lastReportId: Long?
    ): MutableList<Report>

    fun findReportsBeforeCreatedAtPaging(
        size: Long,
        lastCreatedAt: LocalDateTime?
    ): MutableList<Report>
}