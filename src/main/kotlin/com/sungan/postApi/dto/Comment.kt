package com.sungan.postApi.dto

import com.sungan.postApi.domain.UserInfo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

class Comment

data class CommentVo(
    val commentId: Long,
    val userInfo: UserInfo,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val likeCnt: Long, // like 한 사람은 따로 보여줘야함 TODO: 다른 페이지 있냐고 물어보기
    val nestedComments: List<NestedCommentVo>
): CommentBaseVo

interface CommentBaseVo

open class BestCommentVo(
    open val id: Long,
    open val content: String,
    open val userInfo: UserInfo,
    open val createdAt: LocalDateTime,
    open val updatedAt: LocalDateTime,
)

class SunganBestCommentVo(
    override val id: Long,
    override val content: String,
    override val userInfo: UserInfo,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime,
    val sunganId: Long,
): BestCommentVo(id, content, userInfo, createdAt, updatedAt)

data class CommentWithLikeCntAndIsLiked<T>(
    val id: Long,
    val content: String,
    val userInfo: UserInfo,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val likeCnt: Long,
    val isLiked: Boolean,
    val nestedComments: List<T>,
)

data class PostCommentRequestDto(
    @ApiModelProperty(required = true, example = "댓글 내용입니다.")
    val content: String,
    @ApiModelProperty(required = true, example = "1")
    val sunganId: Long,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

data class PatchCommentRequestDto(
    @ApiModelProperty(required = true, example = "1")
    val commentId: Long,
    @ApiModelProperty(required = true, example = "수정된 댓글 내용입니다.")
    val content: String
)

data class PatchNestedCommentRequestDto(
    @ApiModelProperty(required = true, example = "수정된 댓글 내용입니다.")
    val content: String
)

data class NestedCommentVo(
    val id: Long,
    val userInfo: UserInfo,
    val content: String,
    val commentId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class PostNestedCommentReqDto(
    val content: String,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

