package com.sungan.postApi.repository.query

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.report.QReportLike.reportLike
import com.sungan.postApi.domain.report.Report

class ReportLikeQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): ReportLikeQueryRepository {
    override fun deleteAllByReport(report: Report) {
        queryFactory.delete(reportLike).where(reportLike.report.eq(report)).execute()
    }
}