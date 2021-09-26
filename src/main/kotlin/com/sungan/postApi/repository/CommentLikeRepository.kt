package com.sungan.postApi.repository

import com.sungan.postApi.domain.Comment
import com.sungan.postApi.domain.CommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, Long> {
    fun findByComment(comment: Comment): MutableList<CommentLike>
}