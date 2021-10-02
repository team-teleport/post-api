package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.QSungan.sungan
import com.sungan.postApi.domain.QUserViewdSungan.userViewdSungan
import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.dto.GetMainRequestDto

class SunganQueryRepositoryImpl(
    val query: JPAQueryFactory
) : SunganQueryRepository {
    override fun findMainByUserIdAndVehicleAndPaging(
        userId: Long,
        getMainRequestDto: GetMainRequestDto
    ): MutableList<Sungan> {
        return query.selectFrom(sungan)
            .where(sungan.id.lt(getMainRequestDto.lastSunganId),sungan.vehicle.name.eq(getMainRequestDto.vehicleName))
            .leftJoin(userViewdSungan.sungan)
            .on(userViewdSungan.sungan.eq(sungan))
            .where(userViewdSungan.user_id.isNull)
            .orderBy(sungan.createdAt.desc())
            .limit(10)
            .fetch()
    }
}