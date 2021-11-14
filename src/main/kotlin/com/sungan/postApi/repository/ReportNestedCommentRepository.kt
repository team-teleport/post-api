package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.ReportComment
import com.sungan.postApi.domain.report.ReportNestedComment
import org.springframework.data.jpa.repository.JpaRepository

interface ReportNestedCommentRepository : JpaRepository<ReportNestedComment, Long> {
    fun findByReportComment(reportComment: ReportComment): MutableList<ReportNestedComment>
}