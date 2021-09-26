package com.sungan.postApi.repository

import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.domain.SunganLike
import org.springframework.data.jpa.repository.JpaRepository

interface SunganLikeRepository : JpaRepository<SunganLike, Long> {
    fun findByUserIdAndSungan(userId: Long, sungan: Sungan): SunganLike?
}