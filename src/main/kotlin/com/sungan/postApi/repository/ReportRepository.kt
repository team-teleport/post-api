package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.Report
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ReportRepository: JpaRepository<Report, Long> {
    fun findByUserInfoUserId(userId: Long): MutableList<Report>
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): MutableList<Report>
}