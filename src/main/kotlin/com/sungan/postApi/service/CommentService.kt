package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Comment
import com.sungan.postApi.domain.NestedComment
import com.sungan.postApi.dto.*
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service

@Service
class CommentService(
    val sunganRepository: SunganRepository,
    val commentRepository: CommentRepository,
    val nestedCommentRepository: NestedCommentRepository,
    val sunganLikeRepository: SunganLikeRepository,
    val commentLikeRepository: CommentLikeRepository
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
                postCommentRequestDto.makeUserInfo(userId),
                sungan
            )
        )
        return comment.convertToVo()
    }

    fun destroyComment(userId: Long, id: Long): CommentVo {
        val comment = commentRepository.findById(id).orElseThrow { SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != comment.userInfo.userId) throw SunganException(SunganError.FORBIDDEN)
        val vo = comment.convertToVo()
        commentRepository.delete(comment)
        return vo
    }

    fun updateComment(userId: Long, patchCommentRequestDto: PatchCommentRequestDto): CommentVo {
        val comment = commentRepository.findById(patchCommentRequestDto.commentId)
            .orElseThrow { SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != comment.userInfo.userId) throw SunganException(SunganError.FORBIDDEN)
        comment.content = patchCommentRequestDto.content
        commentRepository.save(comment)
        return comment.convertToVo()
    }

    fun createNestedComment(userId: Long, commentId: Long, postNestedCommentReqDto: PostNestedCommentReqDto) {
        val comment =
            commentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        nestedCommentRepository.save(
            NestedComment(
                comment,
                postNestedCommentReqDto.makeUserInfo(userId),
                postNestedCommentReqDto.content
            )
        )
    }

    fun readCommentsWithLikes(userId: Long, sunganId: Long): List<CommentWithLikeCntAndIsLiked<NestedCommentVo>> {
        val sungan = sunganRepository.findById(sunganId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val comments = commentRepository.findBySungan(sungan)
        return comments.asSequence().map { comment ->
            CommentWithLikeCntAndIsLiked(
                comment.content,
                comment.userInfo.userId,
                comment.createdAt,
                comment.updatedAt,
                comment.likes.size.toLong(),
                commentLikeRepository.findByUserIdAndComment(userId, comment) != null,
                comment.nestedComments.map { nestedComment -> nestedComment.convertToVo() }
            )
        }.toList()
    }
}