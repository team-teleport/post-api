package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceLike
import com.sungan.postApi.dto.HotplaceVo
import com.sungan.postApi.dto.HotplaceWithLikeCommendCntVo
import com.sungan.postApi.dto.PostHotplaceReqDto
import com.sungan.postApi.dto.UpdateHotplaceReqDto
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotplaceService(
    val hotplaceRepository: HotplaceRepository,
    val line2StationRepository: Line2StationRepository,
    val hotplaceCommentRepository: HotplaceCommentRepository,
    val hotplaceLikeRepository: HotplaceLikeRepository,
    val hotplaceCommentLikeRepository: HotplaceCommentLikeRepository,
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
        deleteHotplaceCascade(hotplace)
    }

    private fun deleteHotplaceCascade(hotplace: Hotplace) {
        hotplaceLikeRepository.deleteAllByHotplace(hotplace)

        hotplaceCommentLikeRepository.deleteAllByHotplace(hotplace)
        hotplaceCommentRepository.deleteAllByHotplace(hotplace) // TODO: 이거 query repository에서 다시 구현. 지금 이 메서드 실행 마지막에 hotplace도 지우고 있음.

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