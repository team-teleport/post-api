package com.sooni.postapi.dto

import com.sooni.postapi.domain.DetailHashTag
import com.sooni.postapi.domain.MainHashTag
import com.sooni.postapi.domain.SunganContent
import com.sooni.postapi.domain.User
import io.swagger.annotations.ApiModelProperty

class Sungan

data class SunganDto(
    val isOwnSungan: Boolean, // 유저의 글인지 아닌지
    val sungan: SunganVo
)

data class SunganVo(
    val id: Long,
    val title: String,
    val text: String,
    val contents: List<SunganContentVo>,
    val emoji: String?,
    val mainHashTag: MainHashTag?,
    val detailHashTag: List<DetailHashTag>,
    val user: UserVo,
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
    val user: User?,
    val sunganId: Long
)