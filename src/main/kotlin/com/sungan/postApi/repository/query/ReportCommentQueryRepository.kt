package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportComment

interface ReportCommentQueryRepository {
    fun findByReportOrderByLikes(report: Report): ReportComment?
}