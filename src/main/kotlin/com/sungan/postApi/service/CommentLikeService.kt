package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.CommentLike
import com.sungan.postApi.dto.CommentLikeVo
import com.sungan.postApi.repository.CommentLikeRepository
import com.sungan.postApi.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentLikeService(
    val commentLikeRepository: CommentLikeRepository,
    val commentRepository: CommentRepository
) {
    fun createCommentLike(userId: Long, commentId: Long): CommentLikeVo {
        val comment =
            commentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        commentLikeRepository.findByUserIdAndComment(userId, comment)
            ?.let { throw SunganException(SunganError.DUPLICATE) }
        return commentLikeRepository.save(CommentLike(
            userId,
            commentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        )).convertToVo()
    }

    fun destroyCommentLike(userId: Long, commentLikeId: Long) {
        val commentLike =
            commentLikeRepository.findById(commentLikeId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        if (commentLike.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        commentLikeRepository.delete(commentLike)
    }
}