package com.sungan.postApi.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.querydsl.core.annotations.QueryProjection
import com.sungan.postApi.domain.MainHashTag
import com.sungan.postApi.domain.SunganContent
import io.swagger.annotations.ApiModelProperty

class Sungan

data class SunganDto(
    val isOwnSungan: Boolean, // 유저의 글인지 아닌지
    val sungan: SunganVo
)

@JsonInclude(JsonInclude.Include.NON_NULL)
class SunganVo @QueryProjection constructor(
    val id: Long,
    val title: String,
    val text: String,
    val contents: List<SunganContentVo>,
    val emoji: String?,
    val mainHashTag: MainHashTag?,
    val detailHashTag: List<DetailHashTagVo>,
    val userId: Long,
    val comments: List<CommentVo>,
    val readCnt: Long,
    val likeCnt: Long,
)

data class SunganContentVo(
    val contentType: SunganContent.ContentType,
    val url: String,
)

data class CreateSunganRequestDto(
    @ApiModelProperty(required = true, example = "제목입니다.")
    val title: String,
    @ApiModelProperty(required = true, example = "내용입니다.")
    val text: String,
    @ApiModelProperty(required = true, example = "1")
    val vehicleId: Long,
    @ApiModelProperty(example = "😃")
    val emoji: String?,
    @ApiModelProperty(example = "1")
    val mainHashTagId: Long?,
    @ApiModelProperty(example = "[\"상세태그1\", \"상세태그2\"]")
    val detailHashTag: List<String>?
)

data class ReadSunganDto(
    val userId: Long?,
    val sunganId: Long
)

data class PatchSunganRequestDto(
    @ApiModelProperty(required = true, example = "1")
    val sunganId: Long,
    @ApiModelProperty(example = "수정된 제목입니다.")
    val title: String?,
    @ApiModelProperty(example = "수정된 내용입니다.")
    val text: String?,
    @ApiModelProperty(example = "😃")
    val emoji: String?,
    @ApiModelProperty(example = "1")
    val mainHashTagId: Long?,
    @ApiModelProperty
    val detailHashTag: List<String>?
)