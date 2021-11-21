package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.sungan.CommentLike
import com.sungan.postApi.dto.CommentLikeVo
import com.sungan.postApi.event.publisher.LikeType
import com.sungan.postApi.event.publisher.NotiEventPublisher
import com.sungan.postApi.repository.CommentLikeRepository
import com.sungan.postApi.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentLikeService(
    val commentLikeRepository: CommentLikeRepository,
    val commentRepository: CommentRepository,
    val notiEventPublisher: NotiEventPublisher,
) {
    fun createCommentLike(userId: Long, commentId: Long): CommentLikeVo {
        val comment =
            commentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        commentLikeRepository.findByUserIdAndComment(userId, comment)
            ?.let { throw SunganException(SunganError.DUPLICATE) }
        val newLike = commentLikeRepository.save(CommentLike(
            userId,
            commentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        ))
        notiEventPublisher.publishLikeRegisteredEvent(comment.userInfo.userId, newLike.userId, LikeType.Comment)
        return newLike.convertToVo()
    }

    fun destroyCommentLike(userId: Long, commentId: Long) {
        val comment = commentRepository.findById(commentId).orElseThrow { SunganException(SunganError.BAD_REQUEST) }
        val commentLike =
            commentLikeRepository.findByUserIdAndComment(userId, comment)
                ?: throw SunganException(SunganError.DUPLICATE, "좋아요 취소 요청 중복")
        if (commentLike.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        commentLikeRepository.delete(commentLike)
    }
}