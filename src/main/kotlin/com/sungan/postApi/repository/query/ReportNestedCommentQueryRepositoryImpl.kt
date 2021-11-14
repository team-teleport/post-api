package com.sungan.postApi.repository.query

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.report.QReportComment.reportComment
import com.sungan.postApi.domain.report.QReportNestedComment.reportNestedComment
import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportComment

class ReportNestedCommentQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): ReportNestedCommentQueryRepository {
    override fun deleteAllByReport(report: Report) {
        val comments = queryFactory.selectFrom(reportComment).where(reportComment.report.eq(report))
        queryFactory.update(reportNestedComment)
            .set(reportNestedComment.deleted, true)
            .where(reportNestedComment.reportComment.`in`(comments))
            .execute()
    }

    override fun deleteAllByReportComment(reportComment: ReportComment) {
        queryFactory.update(reportNestedComment)
            .set(reportNestedComment.deleted, true)
            .where(reportNestedComment.reportComment.eq(reportComment))
            .execute()
    }
}