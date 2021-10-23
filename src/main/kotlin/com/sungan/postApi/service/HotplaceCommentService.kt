package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.HotplaceComment
import com.sungan.postApi.dto.HotplaceCommentVo
import com.sungan.postApi.dto.PostHotplaceCommentReqDto
import com.sungan.postApi.repository.HotplaceCommentRepository
import com.sungan.postApi.repository.HotplaceRepository
import org.springframework.stereotype.Service

@Service
class HotplaceCommentService(
    val hotplaceRepository: HotplaceRepository,
    val hotplaceCommentRepository: HotplaceCommentRepository
) {
    fun readHotplaceCommentList(userId: Long, hotPlaceId: Long): List<HotplaceCommentVo> {
        val hotplace =
            hotplaceRepository.findById(hotPlaceId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val comments = hotplaceCommentRepository.findByHotplaceOrderByCreatedAtDesc(hotplace)
        return comments.asSequence().map { comment ->
            comment.convertToVo()
        }.toList()
    }

    fun createHotplaceComment(userId: Long, postHotplaceCommentReqDto: PostHotplaceCommentReqDto) {
        val hotplace = hotplaceRepository.findById(postHotplaceCommentReqDto.hotplaceId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val comment = hotplaceCommentRepository.save(HotplaceComment(
            postHotplaceCommentReqDto.content,
            userId,
            hotplace
        ))
    }
}