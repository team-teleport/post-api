package com.sungan.postApi.repository

import com.sungan.postApi.domain.sungan.Sungan
import com.sungan.postApi.domain.sungan.SunganLike
import org.springframework.data.jpa.repository.JpaRepository

interface SunganLikeRepository : JpaRepository<SunganLike, Long> {
    fun findByUserIdAndSungan(userId: Long, sungan: Sungan): SunganLike?
}