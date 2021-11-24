package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceLike
import com.sungan.postApi.dto.*
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
    fun readHotplace(
        userId: Long,
        getMainRequestDto: GetMainRequestDto,
        stationName: String? = null
    ): List<PostBaseWithLikeByUserAndBestComment> {
        var station: Line2Station? = null
        if (stationName != null) {
            station = line2StationRepository.findByName(stationName) ?: throw SunganException(
                SunganError.ENTITY_NOT_FOUND,
                "해당 이름의 역을 찾을 수 없습니다."
            )
        }
        return hotplaceRepository.findHotplacesAfterLastHotplacePagingOrderByCreatedAtDesc(
            getMainRequestDto.size,
            getMainRequestDto.lastId,
            station
        ).map { hotplace -> PostBaseWithLikeByUserAndBestComment(
            hotplace.convertToVo(),
            com.sungan.postApi.dto.PostType.PLACE,
            hotplaceLikeRepository.existsByHotplaceAndUserId(hotplace, userId),
            hotplaceCommentRepository.findByHotplaceOrderByLikes(hotplace)?.convertToVo()
        ) }
    }

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

    @Transactional
    fun updateHotplace(userId: Long, updateHotplaceReqDto: UpdateHotplaceReqDto): HotplaceVo {
        val hotplace =
            hotplaceRepository.findById(updateHotplaceReqDto.id)
                .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (hotplace.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        if (updateHotplaceReqDto.text != null) hotplace.text = updateHotplaceReqDto.text
        if (updateHotplaceReqDto.emoji != null) hotplace.emoji = updateHotplaceReqDto.emoji
        updateHotplaceReqDto.stationName?.let {
            val station = line2StationRepository.findByName(it) ?: throw SunganException(SunganError.BAD_REQUEST)
            hotplace.station = station
        }
        if (updateHotplaceReqDto.place != null) hotplace.place = updateHotplaceReqDto.place
        return hotplace.convertToVo()
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