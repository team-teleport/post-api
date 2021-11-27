package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.repository.query.ReportQueryRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ReportRepository: JpaRepository<Report, Long>, ReportQueryRepository {
    fun findByUserInfoUserId(userId: Long): MutableList<Report>
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): MutableList<Report>
}