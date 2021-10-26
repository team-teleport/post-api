package com.sungan.postApi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.sungan.postApi.domain.ReportType
import com.sungan.postApi.domain.SunganChannel
import com.sungan.postApi.domain.SunganContent
import io.swagger.annotations.ApiModelProperty

class Sungan

data class SunganDto(
    val isOwnSungan: Boolean, // 유저의 글인지 아닌지
    val sungan: SunganVo
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SunganVo(
    val id: Long,
    val station: Line2StationVo,
    val channel: SunganChannel,
    val text: String,
    val contents: List<SunganContentVo>,
    val emoji: String?,
    val userId: Long,
    val comments: List<CommentVo>,
    val readCnt: Long,
    val likeCnt: Long,
)


data class VehicleVo(
    val colorCode: String,
    val name: String,
    val Type: String,
)

data class SunganContentVo(
    val contentType: SunganContent.ContentType,
    val url: String,
)

data class CreateSunganRequestDto(
    @ApiModelProperty(required = true, example = "내용입니다.")
    val text: String,
    @ApiModelProperty(required = true, example = "1")
    val channelId: Long,
//    @ApiModelProperty(required = true, example = "9호선")
//    val vehicleName: String,
    @ApiModelProperty(required = true, example = "성수")
    val stationName: String,
    @ApiModelProperty(example = "😃")
    val emoji: String?
)

data class ReadSunganDto(
    val userId: Long?,
    val sunganId: Long
)

data class PatchSunganRequestDto(
    @ApiModelProperty(required = true, example = "1")
    val sunganId: Long,
    @ApiModelProperty(example = "수정된 내용입니다.")
    val text: String?,
    @ApiModelProperty(example = "🥶")
    val emoji: String?
)