package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.repository.query.HotplaceQueryRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface HotplaceRepository : JpaRepository<Hotplace, Long>, HotplaceQueryRepository {
    fun findByUserInfoUserId(userId: Long): MutableList<Hotplace>
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): MutableList<Hotplace>
    fun findByStationAndCreatedAtBetween(
        station: Line2Station,
        start: LocalDateTime,
        end: LocalDateTime
    ): MutableList<Hotplace>
}