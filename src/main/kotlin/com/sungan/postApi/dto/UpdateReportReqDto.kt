package com.sungan.postApi.dto

data class UpdateReportReqDto(
    val id: Long,
    val detail: String?,
    val vehicleIdNum: String? = null,
    val shouldBeUploaded: Boolean? = true,
)
