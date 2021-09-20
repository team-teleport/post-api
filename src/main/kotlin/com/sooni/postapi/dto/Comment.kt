package com.sooni.postapi.dto

import java.time.LocalDateTime

class Comment

data class CommentVo(
    val commentId: Long,
    val user: UserVo,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val likes: List<CommentLikeVo>
)