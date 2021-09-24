package com.sooni.postapi.service

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.domain.Comment
import com.sooni.postapi.dto.CommentVo
import com.sooni.postapi.dto.PatchCommentRequestDto
import com.sooni.postapi.dto.PostCommentRequestDto
import com.sooni.postapi.repository.CommentRepository
import com.sooni.postapi.repository.SunganRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    val sunganRepository: SunganRepository,
    val commentRepository: CommentRepository
) {
    fun createComment(userId: Long, postCommentRequestDto: PostCommentRequestDto): CommentVo {
        val sungan = sunganRepository.findById(postCommentRequestDto.sunganId).orElseThrow {
            throw SunganException(
                SunganError.BAD_REQUEST_INVALID_ID
            )
        }
        val comment = commentRepository.save(
            Comment(
                postCommentRequestDto.content,
                userId,
                sungan
            )
        )
        return comment.convertToVo()
    }

    fun destroyComment(userId: Long, id: Long): CommentVo {
        val comment = commentRepository.findById(id).orElseThrow { SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != comment.userId) throw SunganException(SunganError.FORBIDDEN)
        val vo = comment.convertToVo()
        commentRepository.delete(comment)
        return vo
    }

    fun updateComment(userId: Long, patchCommentRequestDto: PatchCommentRequestDto): CommentVo {
        val comment = commentRepository.findById(patchCommentRequestDto.commentId)
            .orElseThrow { SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != comment.userId) throw SunganException(SunganError.FORBIDDEN)
        comment.content = patchCommentRequestDto.content
        commentRepository.save(comment)
        return comment.convertToVo()
    }
}