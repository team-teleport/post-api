package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceLike
import com.sungan.postApi.dto.HotplaceVo
import com.sungan.postApi.dto.HotplaceWithLikeCommendCntVo
import com.sungan.postApi.dto.PostHotplaceReqDto
import com.sungan.postApi.dto.UpdateHotplaceReqDto
import com.sungan.postApi.event.publisher.NotiEventPublisher
import com.sungan.postApi.event.publisher.NotiType
import com.sungan.postApi.event.publisher.PostType
import com.sungan.postApi.repository.HotplaceCommentRepository
import com.sungan.postApi.repository.HotplaceLikeRepository
import com.sungan.postApi.repository.HotplaceRepository
import com.sungan.postApi.repository.Line2StationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotplaceService(
    private val hotplaceRepository: HotplaceRepository,
    private val line2StationRepository: Line2StationRepository,
    private val hotplaceCommentRepository: HotplaceCommentRepository,
    private val hotplaceLikeRepository: HotplaceLikeRepository,
    private val notiEventPublisher: NotiEventPublisher,
) {
    fun createHotplace(userId: Long, postHotplaceReqDto: PostHotplaceReqDto): HotplaceVo {
        val stationName = postHotplaceReqDto.stationName
        val hotplace = hotplaceRepository.save(
            Hotplace(
                postHotplaceReqDto.text,
                postHotplaceReqDto.makeUserInfo(userId),
                if (stationName != null) line2StationRepository.findByName(stationName) else null
                    ?: throw SunganException(SunganError.BAD_REQUEST),
                postHotplaceReqDto.place,
                postHotplaceReqDto.emoji
            )
        )
        return hotplace.convertToVo()
    }

    @Transactional
    fun destroyHotplace(userId: Long, hotplaceId: Long) {
        val hotplace =
            hotplaceRepository.findById(hotplaceId).orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (hotplace.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        hotplaceRepository.delete(hotplace)
    }

    fun updateHotplace(userId: Long, updateHotplaceReqDto: UpdateHotplaceReqDto) {
        TODO("핫플레이스 업데이트 서비스")
    }

    fun readHotplace(userId: Long, hotplaceId: Long): HotplaceWithLikeCommendCntVo {
        val hotplace =
            hotplaceRepository.findById(hotplaceId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        return HotplaceWithLikeCommendCntVo(
            hotplace.convertToVo(),
            hotplaceLikeRepository.findByHotplaceAndUserId(hotplace, userId) != null,
            hotplace.hotplaceLikes.size.toLong(),
            hotplaceCommentRepository.countByHotplace(hotplace)

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
        if (hotplace.userInfo.userId != userId) {
            notiEventPublisher.publishLikeRegisteredEvent(
                hotplace.userInfo.userId,
                userId,
                NotiType.Post,
                PostType.Hotplace,
                hotplaceId
            )
        }
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