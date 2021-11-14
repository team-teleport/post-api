package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.ReportType
import org.springframework.data.jpa.repository.JpaRepository

interface ReportTypeRepository: JpaRepository<ReportType, Long> {
    fun findByLabel(label: String): ReportType?
}