package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.SunganLike
import com.sungan.postApi.dto.SunganLikeVo
import com.sungan.postApi.repository.SunganLikeRepository
import com.sungan.postApi.repository.SunganRepository
import org.springframework.stereotype.Service

@Service
class SunganLikeService(
    val sunganLikeRepository: SunganLikeRepository,
    val sunganRepository: SunganRepository
) {
    fun createSunganLike(userId: Long, sunganId: Long): SunganLikeVo {
        val sungan = sunganRepository.findById(sunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        sunganLikeRepository.findByUserIdAndSungan(userId, sungan)?.let { throw SunganException(SunganError.DUPLICATE) }
        val newLike = sunganLikeRepository.save(
            SunganLike(
                userId, sungan
            )
        )
        return newLike.convertToVo()
    }
}