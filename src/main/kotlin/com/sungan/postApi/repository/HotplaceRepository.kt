package com.sungan.postApi.repository

import com.sungan.postApi.domain.Hotplace
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceRepository: JpaRepository<Hotplace, Long> {
    fun findByUserId(userId: Long): MutableList<Hotplace>
}