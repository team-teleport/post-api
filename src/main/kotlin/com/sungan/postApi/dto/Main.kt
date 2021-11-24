package com.sungan.postApi.dto

import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

class Main

data class GetMainRequestDto(
    @ApiModelProperty(value = "이전 마지막 아이디")
    val lastId: Long? = null,

//    @ApiModelProperty(value = "정렬 기준(0: 최신순, 1: 좋아요순, 2: 조회수순)", required = true, example = "0")
//    val orderBy: OrderType,

    @ApiModelProperty(value = "가져오고 싶은 순간 개수")
    val size: Long,
)

data class GetSunganByChannelReqDto(
    @ApiModelProperty(value = "이전 마지막 아이디")
    val lastId: Long? = null,

//    @ApiModelProperty(value = "정렬 기준(0: 최신순, 1: 좋아요순, 2: 조회수순)", required = true, example = "0")
//    val orderBy: OrderType,

    @ApiModelProperty(value = "가져오고 싶은 순간 개수")
    val size: Long,

    val channelId: Long
)

enum class SunganChannel(id: Int){
    MUSIC(1),
    EVENTS(2),
    TALK(3),
}

data class GetAllPostReqDto(
    @ApiModelProperty(value = "마지막 게시글의 createdAt")
    val lastCreatedAt: LocalDateTime? = null,

    @ApiModelProperty(value = "가져오고 싶은 순간 개수")
    val size: Long,
)

enum class OrderType {
    NEW,
    LIKE,
    READ
}

data class SunganWithLikeByUser(
    val sungan: SunganVo,
    val isLiked: Boolean
)

data class PostBaseWithLikeByUserAndBestComment(
    val post: PostBaseVo,
    val type: PostType,
    val didLike: Boolean,
    val bestComment: CommentBaseVo?
)

enum class PostType {
    SUNGAN,
    REPORT,
    PLACE
}