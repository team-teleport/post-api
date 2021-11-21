package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.sungan.SunganLike
import com.sungan.postApi.dto.SunganLikeVo
import com.sungan.postApi.event.publisher.LikeType
import com.sungan.postApi.event.publisher.NotiEventPublisher
import com.sungan.postApi.repository.SunganLikeRepository
import com.sungan.postApi.repository.SunganRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SunganLikeService(
    val sunganLikeRepository: SunganLikeRepository,
    val sunganRepository: SunganRepository,
    val notiEventPublisher: NotiEventPublisher,
) {
    @Transactional
    fun createSunganLike(userId: Long, sunganId: Long): SunganLikeVo {
        val sungan = sunganRepository.findById(sunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        sunganLikeRepository.findByUserIdAndSungan(userId, sungan)?.let { throw SunganException(SunganError.DUPLICATE) }
        val newLike = sunganLikeRepository.save(
            SunganLike(
                userId, sungan
            )
        )
        sungan.likeCnt += 1
        sunganRepository.save(sungan)
        if (userId != sungan.userInfo.userId) {
            notiEventPublisher.publishLikeRegisteredEvent(sungan.userInfo.userId, userId, LikeType.Post)
        }
        return newLike.convertToVo()
    }

    @Transactional
    fun destroySunganLike(userId: Long, sunganId: Long) {
        val sungan = sunganRepository.findById(sunganId).orElseThrow {SunganException(SunganError.BAD_REQUEST)}
        val sunganLike = sunganLikeRepository.findByUserIdAndSungan(userId, sungan)
            ?: throw SunganException(SunganError.BAD_REQUEST_INVALID_ID)
        if (sunganLike.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        sunganLike.sungan.likeCnt -= 1
        sunganRepository.save(sunganLike.sungan)
        sunganLikeRepository.delete(sunganLike)
    }
}