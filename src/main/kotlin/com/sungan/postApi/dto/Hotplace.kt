package com.sungan.postApi.dto

import java.time.LocalDateTime

class Hotplace

data class HotplaceVo(
    val id: Long,
    val title: String,
    val text: String,
    val userId: Long,
    val username: String,
    val profileImage: String?,
    val station: Line2StationVo,
    val place: String,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
): PostBaseVo()

data class PostHotplaceReqDto(
    val title: String,
    val text: String,
    val username: String,
    val profileImage: String,
    val stationName: String,
    val place: String
)

data class HotplaceWithLikeCommendCntVo(
    val hotplace: HotplaceVo,
    val didLike: Boolean,
    val likeCnt: Long,
    val CommentCnt: Long
)

data class HotplaceCommentVo(
    val id: Long,
    val content: String,
    val userId: Long,
    val hotplaceId: Long,
    val nestedComments: List<HotplaceNestedCommentVo>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
): CommentBaseVo

data class PostHotplaceCommentReqDto(
    val hotplaceId: Long,
    val content: String
)

data class PostHotplaceNestedCommentReqDto(
    val commentId: Long,
    val content: String
)

data class HotplaceCommentVoWithLike(
    val comment: HotplaceCommentVo,
    val didLike: Boolean
)

data class HotplaceNestedCommentVo(
    val id: Long,
    val hotplaceCommentId: Long,
    val content: String,
    val userId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)