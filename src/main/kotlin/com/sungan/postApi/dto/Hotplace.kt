package com.sungan.postApi.dto

import com.sungan.postApi.domain.UserInfo
import java.time.LocalDateTime

class Hotplace

data class HotplaceVo(
    val id: Long,
    val title: String,
    val text: String,
    val userInfo: UserInfo,
    val station: Line2StationVo,
    val place: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
): PostBaseVo()

data class PostHotplaceReqDto(
    val title: String,
    val text: String,
    val stationName: String,
    val place: String,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

data class HotplaceWithLikeCommendCntVo(
    val hotplace: HotplaceVo,
    val didLike: Boolean,
    val likeCnt: Long,
    val CommentCnt: Long
)

data class HotplaceCommentVo(
    val id: Long,
    val content: String,
    val userInfo: UserInfo,
    val hotplaceId: Long,
    val nestedComments: List<HotplaceNestedCommentVo>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
): CommentBaseVo

data class PostHotplaceCommentReqDto(
    val hotplaceId: Long,
    val content: String,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

data class PostHotplaceNestedCommentReqDto(
    val commentId: Long,
    val content: String,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

data class HotplaceCommentVoWithLike(
    val comment: HotplaceCommentVo,
    val didLike: Boolean
)

data class HotplaceNestedCommentVo(
    val id: Long,
    val hotplaceCommentId: Long,
    val content: String,
    val userInfo: UserInfo,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)