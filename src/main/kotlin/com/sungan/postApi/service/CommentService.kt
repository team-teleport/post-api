package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.sungan.Comment
import com.sungan.postApi.domain.sungan.NestedComment
import com.sungan.postApi.dto.*
import com.sungan.postApi.event.publisher.NotiEventPublisher
import com.sungan.postApi.repository.CommentLikeRepository
import com.sungan.postApi.repository.CommentRepository
import com.sungan.postApi.repository.NestedCommentRepository
import com.sungan.postApi.repository.SunganRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val sunganRepository: SunganRepository,
    private val commentRepository: CommentRepository,
    private val nestedCommentRepository: NestedCommentRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val notiEventPublisher: NotiEventPublisher,
) {
    fun createComment(userId: Long, postCommentRequestDto: PostCommentRequestDto): CommentVo {
        val sungan = sunganRepository.findById(postCommentRequestDto.sunganId).orElseThrow {
            SunganException(
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

        if (userId != sungan.userInfo.userId) {
            notiEventPublisher.publishCommentRegisteredEvent(sungan.userInfo.userId, comment.userInfo.userName)
        }

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
        val newNestedComment = nestedCommentRepository.save(
            NestedComment(
                comment,
                postNestedCommentReqDto.makeUserInfo(userId),
                postNestedCommentReqDto.content
            )
        )

        if (comment.sungan.userInfo.userId != userId) {
            notiEventPublisher.publishCommentRegisteredEvent(
                comment.sungan.userInfo.userId,
                newNestedComment.userInfo.userName
            )
        }

        if(comment.userInfo.userId != userId) {
            notiEventPublisher.publishNestedCommentRegisteredEvent(
                comment.userInfo.userId,
                newNestedComment.userInfo.userName
            )
        }
    }

    fun readCommentsWithLikes(userId: Long, sunganId: Long): List<CommentWithLikeCntAndIsLiked<NestedCommentVo>> {
        val sungan = sunganRepository.findById(sunganId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val comments = commentRepository.findBySungan(sungan)
        return comments.asSequence().map { comment ->
            CommentWithLikeCntAndIsLiked(
                comment.id!!,
                comment.content,
                comment.userInfo,
                comment.createdAt,
                comment.updatedAt,
                comment.likes.size.toLong(),
                commentLikeRepository.findByUserIdAndComment(userId, comment) != null,
                comment.nestedComments.map { nestedComment -> nestedComment.convertToVo() }
            )
        }.toList()
    }

    fun destroyNestedComment(userId: Long, nestedCommentId: Long) {
        val nestedComment = nestedCommentRepository.findById(nestedCommentId)
            .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (nestedComment.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        nestedCommentRepository.delete(nestedComment)
    }

    fun updateNestedComment(
        userId: Long,
        nestedCommentId: Long,
        patchNestedCommentRequestDto: PatchNestedCommentRequestDto
    ) {
        val nestedComment = nestedCommentRepository.findById(nestedCommentId)
            .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (nestedComment.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        nestedComment.content = patchNestedCommentRequestDto.content
    }
}