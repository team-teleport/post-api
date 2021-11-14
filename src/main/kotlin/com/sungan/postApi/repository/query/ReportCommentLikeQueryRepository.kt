package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.report.Report

interface ReportCommentLikeQueryRepository {
    fun deleteAllByReport(report: Report)
}