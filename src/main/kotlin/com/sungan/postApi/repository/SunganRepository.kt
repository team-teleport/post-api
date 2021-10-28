package com.sungan.postApi.repository

import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.Sungan
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface SunganRepository : JpaRepository<Sungan, Long>, SunganQueryRepository {
    fun findByUserInfoUserId(userId: Long): MutableList<Sungan>
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): MutableList<Sungan>
    fun findByStationAndCreatedAtBetween(
        station: Line2Station,
        start: LocalDateTime,
        end: LocalDateTime
    ): MutableList<Sungan>
}