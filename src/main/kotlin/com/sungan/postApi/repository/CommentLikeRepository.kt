package com.sungan.postApi.repository

import com.sungan.postApi.domain.sungan.Comment
import com.sungan.postApi.domain.sungan.CommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, Long> {
    fun findByComment(comment: Comment): MutableList<CommentLike>
    fun findByUserIdAndComment(userId: Long, comment: Comment): CommentLike?
}