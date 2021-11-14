package com.sungan.postApi.repository.query

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.report.QReport.report
import com.sungan.postApi.domain.report.QReportComment.reportComment
import com.sungan.postApi.domain.report.QReportCommentLike.reportCommentLike
import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportComment

class ReportCommentLikeQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ReportCommentLikeQueryRepository {
    override fun deleteAllByReport(r: Report) {
        val reportComments =
            queryFactory.selectFrom(reportComment)
                .join(report).on(reportComment.report.eq(report).and(report.eq(r)))
                .fetch()
        queryFactory.delete(reportCommentLike)
            .where(reportCommentLike.reportComment.`in`(reportComments)).execute()
    }

    override fun deleteAllByReportComment(reportComment: ReportComment) {
        queryFactory.delete(reportCommentLike)
            .where(reportCommentLike.reportComment.eq(reportComment))
    }
}