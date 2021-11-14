package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.report.Report

interface ReportLikeQueryRepository {
    fun deleteAllByReport(report: Report)
}