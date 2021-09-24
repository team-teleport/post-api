package com.sooni.postapi.service

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.domain.SunganLike
import com.sooni.postapi.dto.SunganLikeVo
import com.sooni.postapi.repository.SunganLikeRepository
import com.sooni.postapi.repository.SunganRepository
import org.springframework.stereotype.Service

@Service
class SunganLikeService(
    val sunganLikeRepository: SunganLikeRepository,
    val sunganRepository: SunganRepository
) {
    fun createSunganLike(userId: Long, sunganId: Long): SunganLikeVo {
        val sungan = sunganRepository.findById(sunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        val alreadyLike =
            sunganLikeRepository.findByUserIdAndSungan(userId, sungan)?.let { throw SunganException(SunganError.DUPLICATE) }
        val newLike = sunganLikeRepository.save(
            SunganLike(
                userId, sungan
            )
        )
        return newLike.convertToVo()
    }
}