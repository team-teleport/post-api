package com.sungan.postApi.repository

import com.sungan.postApi.domain.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository: JpaRepository<Report, Long> {
}