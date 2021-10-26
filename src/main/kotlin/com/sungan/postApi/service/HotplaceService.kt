package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Hotplace
import com.sungan.postApi.domain.HotplaceLike
import com.sungan.postApi.dto.HotplaceVo
import com.sungan.postApi.dto.HotplaceWithLikeCommendCntVo
import com.sungan.postApi.dto.PostHotplaceReqDto
import com.sungan.postApi.repository.HotplaceCommentRepository
import com.sungan.postApi.repository.HotplaceLikeRepository
import com.sungan.postApi.repository.HotplaceRepository
import com.sungan.postApi.repository.Line2StationRepository
import org.springframework.stereotype.Service

@Service
class HotplaceService(
    val hotplaceRepository: HotplaceRepository,
    val line2StationRepository: Line2StationRepository,
    val hotplaceCommentRepository: HotplaceCommentRepository,
    val hotplaceLikeRepository: HotplaceLikeRepository
) {
    fun createHotplace(userId: Long, postHotplaceReqDto: PostHotplaceReqDto): HotplaceVo {
        val hotplace = hotplaceRepository.save(
            Hotplace(
                postHotplaceReqDto.title,
                postHotplaceReqDto.text,
                userId,
                line2StationRepository.findByName(postHotplaceReqDto.stationName)
                    ?: throw SunganException(SunganError.BAD_REQUEST),
                postHotplaceReqDto.place
            )
        )
        return hotplace.convertToVo()
    }

    fun readHotplace(userId: Long, hotplaceId: Long): HotplaceWithLikeCommendCntVo {
        val hotplace =
            hotplaceRepository.findById(hotplaceId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        return HotplaceWithLikeCommendCntVo(
            hotplace.convertToVo(),
            hotplaceLikeRepository.findByHotplaceAndUserId(hotplace, userId) != null,
            hotplaceCommentRepository.countByHotplace(hotplace),
            hotplaceLikeRepository.countByHotplace(hotplace)
        )
    }

    fun createHotplaceLike(userId: Long, hotplaceId: Long) {
        val hotplace =
            hotplaceRepository.findById(hotplaceId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        hotplaceLikeRepository.findByHotplaceAndUserId(hotplace, userId)
            ?.let { throw SunganException(SunganError.DUPLICATE, "이미 좋아요한 게시물입니다.") }
        hotplaceLikeRepository.save(
            HotplaceLike(
                userId, hotplace
            )
        )
    }

    fun destroyHotplaceLike(userId: Long, hotplaceId: Long) {
        val hotplace =
            hotplaceRepository.findById(hotplaceId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val hotplaceLike = hotplaceLikeRepository.findByHotplaceAndUserId(hotplace, userId) ?: throw SunganException(
            SunganError.BAD_REQUEST
        )
        hotplaceLikeRepository.delete(hotplaceLike)
    }
}