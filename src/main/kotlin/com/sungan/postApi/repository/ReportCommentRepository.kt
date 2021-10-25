package com.sungan.postApi.repository

import com.sungan.postApi.domain.Report
import com.sungan.postApi.domain.ReportComment
import org.springframework.data.jpa.repository.JpaRepository

interface ReportCommentRepository:JpaRepository<ReportComment, Long> {
    fun findByReport(report: Report): MutableList<ReportComment>
}