package com.sungan.postApi.dto

class Hotplace

data class HotplaceVo(
    val id: Long,
    val title: String,
    val text: String,
    val userId: Long,
    val station: Line2StationVo,
    val place: String
)

data class PostHotplaceReqDto(
    val title: String,
    val text: String,
    val stationName: String,
    val place: String
)

data class HotplaceCommentVo(
    val id: Long,
    val content: String,
    val userId: Long,
    val hotplaceId: Long,
    val nestedComments: List<HotplaceNestedCommentVo>
)

data class PostHotplaceCommentReqDto(
    val hotplaceId: Long,
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
)