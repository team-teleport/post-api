package com.sungan.postApi.dto

import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

class Comment

data class CommentVo(
    val commentId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val likes: List<CommentLikeVo>
)

data class PostCommentRequestDto(
    @ApiModelProperty(required = true, example = "댓글 내용입니다.")
    val content: String,
    @ApiModelProperty(required = true, example = "1")
    val sunganId: Long
)

data class PatchCommentRequestDto(
    @ApiModelProperty(required = true, example = "1")
    val commentId: Long,
    @ApiModelProperty(required = true, example = "수정된 댓글 내용입니다.")
    val content: String
)