package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportLike
import com.sungan.postApi.repository.query.ReportLikeQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface ReportLikeRepository: JpaRepository<ReportLike, Long>, ReportLikeQueryRepository {
    fun findByReportAndUserId(report: Report, userId: Long): ReportLike?
}