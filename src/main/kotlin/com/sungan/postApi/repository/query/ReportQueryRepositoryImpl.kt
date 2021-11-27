package com.sungan.postApi.repository.query

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.report.QReport.report
import com.sungan.postApi.domain.report.Report
import java.time.LocalDateTime

class ReportQueryRepositoryImpl(
    private val query: JPAQueryFactory
) : ReportQueryRepository {
    override fun findReportsAfterLastHotplacePagingOrderByCreatedAtDesc(
        size: Long,
        lastReportId: Long?
    ): MutableList<Report> {
        val predicate = BooleanBuilder()
        predicate.and(report.shouldBeUploaded.isTrue)
        if (lastReportId != null) predicate.and(report.id.gt(lastReportId))
        return query.selectFrom(report).where(predicate)
            .orderBy(report.id.desc())
            .limit(size)
            .fetch()
    }

    override fun findReportsBeforeCreatedAtPaging(
        size: Long,
        lastCreatedAt: LocalDateTime?
    ): MutableList<Report> {
        val predicate = BooleanBuilder()
        predicate.and(report.shouldBeUploaded.isTrue)
        if (lastCreatedAt != null) predicate.and(
            report.createdAt.before(lastCreatedAt).or(report.createdAt.eq(lastCreatedAt))
        )
        return query.selectFrom(report).where(predicate)
            .orderBy(report.id.desc())
            .limit(size)
            .fetch()
    }
}