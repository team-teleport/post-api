package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.report.Report

interface ReportNestedCommentQueryRepository {
    fun deleteAllByReport(report: Report)
}