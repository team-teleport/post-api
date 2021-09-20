package com.sooni.postapi.repository

import com.sooni.postapi.domain.Comment
import com.sooni.postapi.domain.CommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository: JpaRepository<CommentLike, Long> {
    fun findByComment(comment: Comment): MutableList<CommentLike>
}