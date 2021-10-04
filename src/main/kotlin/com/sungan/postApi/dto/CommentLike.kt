package com.sungan.postApi.dto

import java.time.LocalDateTime

class CommentLike

data class CommentLikeVo (
    val id: Long,
    val commentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime,
        )