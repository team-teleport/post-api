package com.sungan.postApi.repository

import com.sungan.postApi.domain.Hotplace
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface HotplaceRepository: JpaRepository<Hotplace, Long> {
    fun findByUserId(userId: Long): MutableList<Hotplace>
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): MutableList<Hotplace>
}