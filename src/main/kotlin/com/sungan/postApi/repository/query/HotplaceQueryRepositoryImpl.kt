package com.sungan.postApi.repository.query

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.QHotplace.hotplace
import java.time.LocalDateTime

class HotplaceQueryRepositoryImpl(
    private val query: JPAQueryFactory
) : HotplaceQueryRepository {
    override fun findHotplacesAfterLastHotplacePagingOrderByCreatedAtDesc(
        size: Long,
        lastHotplaceId: Long?,
        station: Line2Station?,
    ): MutableList<Hotplace> {
        val predicate = BooleanBuilder()
        if (lastHotplaceId != null) predicate.and(hotplace.id.gt(lastHotplaceId))
        if (station != null) predicate.and(hotplace.station.eq(station))
        return query.selectFrom(hotplace).where(predicate)
            .orderBy(hotplace.id.desc())
            .limit(size)
            .fetch()
    }

    override fun findHotplacesBeforeCreatedAtPaging(
        size: Long,
        lastCreatedAt: LocalDateTime?,
        station: Line2Station?
    ): MutableList<Hotplace> {
        val predicate = BooleanBuilder()
        if (lastCreatedAt != null) predicate.and(
            hotplace.createdAt.before(lastCreatedAt).or(hotplace.createdAt.eq(lastCreatedAt))
        )
        if (station != null) predicate.and(hotplace.station.eq(station))
        return query.selectFrom(hotplace).where(predicate)
            .orderBy(hotplace.id.desc())
            .limit(size)
            .fetch()
    }
}