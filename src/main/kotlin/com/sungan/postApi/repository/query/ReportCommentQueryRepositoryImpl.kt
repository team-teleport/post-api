package com.sungan.postApi.repository.query

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.report.QReportComment.reportComment
import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.domain.report.ReportComment

class ReportCommentQueryRepositoryImpl(
    val query: JPAQueryFactory
) : ReportCommentQueryRepository {
    override fun findByReportOrderByLikes(report: Report): ReportComment? {
        return query.selectFrom(reportComment)
            .where(reportComment.report.eq(report).and(reportComment.report.shouldBeUploaded.isTrue))
            .orderBy(reportComment.likes.size().desc())
            .fetchFirst()
    }
}