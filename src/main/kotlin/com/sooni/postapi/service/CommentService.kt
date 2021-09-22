package com.sooni.postapi.service

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.domain.Comment
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.CommentVo
import com.sooni.postapi.dto.PostCommentRequestDto
import com.sooni.postapi.repository.CommentRepository
import com.sooni.postapi.repository.SunganRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    val sunganRepository: SunganRepository,
    val commentRepository: CommentRepository
) {
    fun createComment(user: User, postCommentRequestDto: PostCommentRequestDto): CommentVo {
        val sungan = sunganRepository.findById(postCommentRequestDto.sunganId).orElseThrow {
            throw SunganException(
                SunganError.BAD_REQUEST_INVALID_ID
            )
        }
        val comment = commentRepository.save(
            Comment(
                postCommentRequestDto.content,
                user,
                sungan
            )
        )
        return comment.convertToVo()
    }
}