package com.sooni.postapi.service

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.domain.SunganLike
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.SunganLikeVo
import com.sooni.postapi.repository.SunganLikeRepository
import com.sooni.postapi.repository.SunganRepository
import org.springframework.stereotype.Service

@Service
class SunganLikeService(
    val sunganLikeRepository: SunganLikeRepository,
    val sunganRepository: SunganRepository
) {
    fun createSunganLike(user: User, sunganId: Long): SunganLikeVo {
        val sungan = sunganRepository.findById(sunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        val alreadyLike =
            sunganLikeRepository.findByUserAndSungan(user, sungan)?.let { throw SunganException(SunganError.DUPLICATE) }
        val newLike = sunganLikeRepository.save(SunganLike(
            user, sungan
        ))
        return newLike.convertToVo()
    }
}