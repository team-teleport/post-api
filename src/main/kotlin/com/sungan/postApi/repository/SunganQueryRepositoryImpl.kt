package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.QSungan.sungan
import com.sungan.postApi.domain.QUserViewedSungan.userViewedSungan
import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.dto.GetMainRequestDto

class SunganQueryRepositoryImpl(
    val query: JPAQueryFactory
) : SunganQueryRepository {
    override fun findSungansAfterLastSunganPaging(
        getMainRequestDto: GetMainRequestDto,
        lastSungan: Sungan
    ): MutableList<Sungan> {
        val query = query.selectFrom(sungan)
//            .where(sungan.vehicle.name.eq(getMainRequestDto.vehicleName))
        return when (getMainRequestDto.orderBy.name) {
            "NEW" -> query
                .where(sungan.createdAt.before(lastSungan.createdAt))
                .orderBy(sungan.createdAt.desc())
                .limit(getMainRequestDto.size)
                .fetch()
            "LIKE" -> query
                .where(
                    sungan.likeCnt.lt(lastSungan.likeCnt)
                        .or(sungan.likeCnt.eq(lastSungan.likeCnt).and(sungan.createdAt.before(lastSungan.createdAt)))
                )
                .orderBy(sungan.likeCnt.desc())
                .orderBy(sungan.createdAt.desc())
                .limit(getMainRequestDto.size)
                .fetch()
            "READ" -> query
                .where(
                    sungan.readCnt.lt(lastSungan.readCnt)
                        .or(sungan.readCnt.eq(lastSungan.readCnt).and(sungan.createdAt.before(lastSungan.createdAt)))
                )
                .orderBy(sungan.readCnt.desc())
                .orderBy(sungan.createdAt.desc())
                .limit(getMainRequestDto.size)
                .fetch()
            else -> throw SunganException(SunganError.BAD_REQUEST)
        }
    }

    override fun findSungansBeforeFirstSunganPaging(
        getMainRequestDto: GetMainRequestDto,
        firstSungan: Sungan
    ): MutableList<Sungan> {
        val query = query.selectFrom(sungan)
//            .where(sungan.vehicle.name.eq(getMainRequestDto.vehicleName))
        return when (getMainRequestDto.orderBy.name) {
            "NEW" -> query
                .where(sungan.createdAt.after(firstSungan.createdAt))
                .orderBy(sungan.createdAt.asc())
                .limit(getMainRequestDto.size)
                .fetch()
            "LIKE" -> query
                .where(
                    sungan.likeCnt.gt(firstSungan.likeCnt)
                        .or(sungan.likeCnt.eq(firstSungan.likeCnt).and(sungan.createdAt.after(firstSungan.createdAt)))
                )
                .orderBy(sungan.likeCnt.asc())
                .orderBy(sungan.createdAt.asc())
                .limit(getMainRequestDto.size)
                .fetch()
            "READ" -> query
                .where(
                    sungan.readCnt.gt(firstSungan.readCnt)
                        .or(sungan.readCnt.eq(firstSungan.readCnt).and(sungan.createdAt.after(firstSungan.createdAt)))
                )
                .orderBy(sungan.readCnt.asc())
                .orderBy(sungan.createdAt.asc())
                .limit(getMainRequestDto.size)
                .fetch()
            else -> throw SunganException(SunganError.BAD_REQUEST)
        }
    }

    override fun findLimitSizeOrderByDesc(userId: Long, getMainRequestDto: GetMainRequestDto): MutableList<Sungan> {
        var query = query.selectFrom(sungan)
//            .where(sungan.vehicle.name.eq(getMainRequestDto.vehicleName))
            .leftJoin(sungan.viewdUsers, userViewedSungan)
            .on(userViewedSungan.userId.eq(userId))
            .where(userViewedSungan.userId.isNull)
        query = when (getMainRequestDto.orderBy.name) {
            "NEW" -> query.orderBy(sungan.createdAt.desc())
            "LIKE" -> query.orderBy(sungan.likeCnt.desc())
            "READ" -> query.orderBy(sungan.readCnt.desc())
            else -> throw SunganException(SunganError.BAD_REQUEST)
        }
        return query.limit(getMainRequestDto.size).fetch()
    }
}