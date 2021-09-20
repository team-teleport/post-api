package com.sooni.postapi.dto

import com.sooni.postapi.domain.DetailHashTag
import com.sooni.postapi.domain.MainHashTag
import com.sooni.postapi.domain.Sungan
import com.sooni.postapi.domain.SunganContent

class Sungan

data class SunganDto(
    val isOwnSungan: Boolean, // 유저의 글인지 아닌지
    val sungan: SunganVo
)

data class SunganVo(
    val title: String,
    val text: String,
    val contents: List<SunganContentVo>,
    val emoji: String?,
    val mainHashTag: MainHashTag?,
    val detailHashTag: List<DetailHashTag>,
    val user: UserVo,
    val comments: List<CommentVo>
) {

    }

data class SunganContentVo(
    val contentType: SunganContent.ContentType,
    val url: String,
)
