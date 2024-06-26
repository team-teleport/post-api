package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceLike
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceLikeRepository : JpaRepository<HotplaceLike, Long> {
    fun countByHotplace(hotplace: Hotplace): Long
    fun findByHotplaceAndUserId(hotplace: Hotplace, userId: Long): HotplaceLike?
    fun existsByHotplaceAndUserId(hotplace: Hotplace, userId: Long): Boolean
}