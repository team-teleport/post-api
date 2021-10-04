package com.sungan.postApi.dto

import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

class CommentLike

data class CommentLikeVo (
    val id: Long,
    val commentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime,
        )

data class PostCommentLike (
    @ApiModelProperty(required = true, example = "1")
    val commentId: Long
)