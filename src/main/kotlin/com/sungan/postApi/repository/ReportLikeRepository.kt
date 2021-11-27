package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportLike
import org.springframework.data.jpa.repository.JpaRepository

interface ReportLikeRepository : JpaRepository<ReportLike, Long> {
    fun findByReportAndUserId(report: Report, userId: Long): ReportLike?
    fun existsByReportAndUserId(report: Report, userId: Long): Boolean
}