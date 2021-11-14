package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportComment
import com.sungan.postApi.repository.query.ReportCommentQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface ReportCommentRepository:JpaRepository<ReportComment, Long>, ReportCommentQueryRepository {
    fun findByReport(report: Report): MutableList<ReportComment>
}