package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportComment

interface ReportCommentLikeQueryRepository {
    fun deleteAllByReport(report: Report)
    fun deleteAllByReportComment(reportComment: ReportComment)
}