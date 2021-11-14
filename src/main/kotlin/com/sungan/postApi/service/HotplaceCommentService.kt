package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.hotplace.HotplaceComment
import com.sungan.postApi.domain.hotplace.HotplaceCommentLike
import com.sungan.postApi.domain.hotplace.HotplaceNestedComment
import com.sungan.postApi.dto.*
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service

@Service
class HotplaceCommentService(
    val hotplaceRepository: HotplaceRepository,
    val hotplaceCommentRepository: HotplaceCommentRepository,
    val hotplaceNestedCommentRepository: HotplaceNestedCommentRepository,
    val hotplaceCommentLikeRepository: HotplaceCommentLikeRepository,
    val hotplaceLikeRepository: HotplaceLikeRepository
) {
    fun readHotplaceCommentList(userId: Long, hotPlaceId: Long): List<CommentWithLikeCntAndIsLiked<HotplaceNestedCommentVo>> {
        val hotplace =
            hotplaceRepository.findById(hotPlaceId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val comments = hotplaceCommentRepository.findByHotplaceOrderByCreatedAtDesc(hotplace)
        return comments.asSequence().map { comment ->
            CommentWithLikeCntAndIsLiked(
                comment.id!!,
                comment.content,
                comment.userInfo,
                comment.createdAt,
                comment.updatedAt,
                comment.likes.size.toLong(),
                hotplaceCommentLikeRepository.findByHotplaceCommentAndUserId(comment, userId) != null,
                comment.nestedComments.asSequence().map { nestedComment -> nestedComment.convertToVo() }.toList()
            )
        }.toList()
    }

    fun createHotplaceComment(userId: Long, postHotplaceCommentReqDto: PostHotplaceCommentReqDto) {
        val hotplace = hotplaceRepository.findById(postHotplaceCommentReqDto.hotplaceId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        hotplaceCommentRepository.save(
            HotplaceComment(
                postHotplaceCommentReqDto.content,
                postHotplaceCommentReqDto.makeUserInfo(userId),
                hotplace
            )
        )
    }

    fun createHotplaceNestedComment(userId: Long, postHotplaceNestedCommentReqDto: PostHotplaceNestedCommentReqDto) {
        val comment = hotplaceCommentRepository.findById(postHotplaceNestedCommentReqDto.commentId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        hotplaceNestedCommentRepository.save(
            HotplaceNestedComment(
                comment,
                postHotplaceNestedCommentReqDto.content,
                postHotplaceNestedCommentReqDto.makeUserInfo(userId)
            )
        )
    }

    fun createHotplaceCommentLike(userId: Long, commentId: Long) {
        val comment =
            hotplaceCommentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        hotplaceCommentLikeRepository.findByHotplaceCommentAndUserId(comment, userId)?.let {
            throw SunganException(SunganError.DUPLICATE, "이미 좋아요 누른 게시물 입니다")
        }
        hotplaceCommentLikeRepository.save(
            HotplaceCommentLike(
                userId, comment
            )
        )
    }

    fun destroyHotplaceCommentLike(userId: Long, commentId: Long) {
        val comment =
            hotplaceCommentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val like =
            hotplaceCommentLikeRepository.findByHotplaceCommentAndUserId(comment, userId) ?: throw SunganException(
                SunganError.DUPLICATE,
                "삭제할 좋아요가 없어요"
            )
        hotplaceCommentLikeRepository.delete(like)
    }
}