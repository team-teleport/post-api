package com.sooni.postapi.dto

import java.time.LocalDateTime

class CommentLike

data class CommentLikeVo (
    val commentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime,
        )