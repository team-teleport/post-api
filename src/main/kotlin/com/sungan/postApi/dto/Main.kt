package com.sungan.postApi.dto

import io.swagger.annotations.ApiModelProperty

class Main

data class GetMainRequestDto (
    @ApiModelProperty(example = "100")
    val lastSunganId: Long = 10000000, // TODO: 여기 어떻게 할지 정하기

    @ApiModelProperty(required = true, example = "2호선")
    val vehicleName: String
        )