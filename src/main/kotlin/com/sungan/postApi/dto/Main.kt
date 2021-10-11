package com.sungan.postApi.dto

import io.swagger.annotations.ApiModelProperty

class Main

data class GetMainRequestDto(
    @ApiModelProperty(required = true, example = "2호선")
    val vehicleName: String,

    @ApiModelProperty(value = "정렬 기준(0: 최신순, 1: 좋아요순, 2: 조회수순)", required = true, example = "0")
    val orderBy: OrderType,

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