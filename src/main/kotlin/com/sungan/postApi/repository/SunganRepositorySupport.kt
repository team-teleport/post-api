package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.QSungan.sungan
import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.domain.Vehicle
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class SunganRepositorySupport(val queryFactory: JPAQueryFactory): QuerydslRepositorySupport(Sungan::class.java) {
    fun findNewSunganByUserIdAndVehicle(userId: Long, vehicle: Vehicle) {
        queryFactory.selectFrom(sungan)
    }
}