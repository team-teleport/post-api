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
        val commentLike = commentLikeRepository.save(CommentLike(
            userId,
            commentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        ))
        return commentLike.convertToVo()
    }
}