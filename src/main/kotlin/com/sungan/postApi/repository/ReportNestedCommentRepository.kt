package com.sungan.postApi.repository

import com.sungan.postApi.domain.ReportComment
import com.sungan.postApi.domain.ReportNestedComment
import org.springframework.data.jpa.repository.JpaRepository

interface ReportNestedCommentRepository:JpaRepository<ReportNestedComment, Long> {
    fun findByReportComment(reportComment: ReportComment): MutableList<ReportNestedComment>
}