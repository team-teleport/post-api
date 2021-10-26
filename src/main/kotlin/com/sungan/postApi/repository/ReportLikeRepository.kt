package com.sungan.postApi.repository

import com.sungan.postApi.domain.Report
import com.sungan.postApi.domain.ReportLike
import org.springframework.data.jpa.repository.JpaRepository

interface ReportLikeRepository: JpaRepository<ReportLike, Long> {
    fun findByReportAndUserId(report: Report, userId: Long): ReportLike?
}