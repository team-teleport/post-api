package com.sungan.postApi.dto

data class Line2StationVo(
    val id: Long,
    val name: String,
    val types: List<Line2TypeVo>
)

data class Line2TypeVo(
    val id: Long,
    val lineType: String
)