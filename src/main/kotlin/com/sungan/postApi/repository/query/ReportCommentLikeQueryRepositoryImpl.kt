package com.sungan.postApi.repository.query

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.report.QReport.report
import com.sungan.postApi.domain.report.QReportComment.reportComment
import com.sungan.postApi.domain.report.Report

class ReportCommentLikeQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ReportCommentLikeQueryRepository {
    override fun deleteAllByReport(r: Report) {
        val reportComments =
            queryFactory.selectFrom(reportComment)
                .join(report).on(reportComment.report.eq(report).and(report.eq(r)))
                .fetch()
        queryFactory.delete(reportComment)
            .where(reportComment.`in`(reportComments)).execute()
    }
}