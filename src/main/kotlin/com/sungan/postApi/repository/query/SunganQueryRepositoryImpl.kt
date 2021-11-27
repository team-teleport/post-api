package com.sungan.postApi.repository.query

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.sungan.QSungan.sungan
import com.sungan.postApi.domain.sungan.Sungan
import com.sungan.postApi.domain.sungan.SunganChannel
import java.time.LocalDateTime

class SunganQueryRepositoryImpl(
    val query: JPAQueryFactory
) : SunganQueryRepository {

    override fun findSungansAfterLastSunganPagingOrderByCreatedAtDesc(
        size: Long,
        lastSunganId: Long?,
        sunganChannel: SunganChannel?,
        station: Line2Station?
    ): MutableList<Sungan> {
        val predicate = BooleanBuilder()
        if (sunganChannel != null) predicate.and(sungan.sunganChannel.eq(sunganChannel))
        if (station != null) predicate.and(sungan.station.eq(station))
        if (lastSunganId != null) predicate.and(sungan.id.gt(lastSunganId))
        return query.selectFrom(sungan).where(predicate)
            .orderBy(sungan.createdAt.desc())
            .limit(size)
            .fetch()
    }

    override fun findSungansBeforeLastCreatedAtPaging(
        size: Long,
        lastCreatedAt: LocalDateTime?,
        station: Line2Station?
    ): MutableList<Sungan> {
        val predicate = BooleanBuilder()
        if (lastCreatedAt != null) predicate.and(sungan.createdAt.before(lastCreatedAt)) // 같은 시간에 올라온건 어케 보여주냐
        if (station != null) predicate.and(sungan.station.eq(station))
        return query.selectFrom(sungan).where(predicate)
            .orderBy(sungan.id.desc())
            .limit(size)
            .fetch()
    }
}