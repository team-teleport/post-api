package com.sungan.postApi.repository

import com.sungan.postApi.domain.Report
import com.sungan.postApi.domain.ReportComment

interface ReportCommentQueryRepository {
    fun findByReportOrderByLikes(report: Report): ReportComment?
}