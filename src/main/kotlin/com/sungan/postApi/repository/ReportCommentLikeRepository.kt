package com.sungan.postApi.repository

import com.sungan.postApi.domain.report.ReportComment
import com.sungan.postApi.domain.report.ReportCommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface ReportCommentLikeRepository : JpaRepository<ReportCommentLike, Long> {
    fun countByReportComment(reportComment: ReportComment): Long
    fun findByReportCommentAndUserId(reportComment: ReportComment, userId: Long): ReportCommentLike?
}