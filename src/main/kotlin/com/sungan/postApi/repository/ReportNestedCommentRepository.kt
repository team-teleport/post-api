package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.ReportComment
import com.sungan.postApi.domain.report.ReportNestedComment
import com.sungan.postApi.repository.query.ReportNestedCommentQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface ReportNestedCommentRepository:JpaRepository<ReportNestedComment, Long>, ReportNestedCommentQueryRepository {
    fun findByReportComment(reportComment: ReportComment): MutableList<ReportNestedComment>
}