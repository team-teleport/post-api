package com.sungan.postApi.dto

import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.ReportType
import com.sungan.postApi.domain.VehicleType

class Report

data class ReportVo(
    var id: Long?,
    var reportType: ReportType,
    var userId: Long,
    var detail: String?,
    var line2Station: Line2StationVo,
    var readCnt: Long,
    var likeCnt: Long
)

data class PostReportReqDto(
    val reportType: ReportType,
    val stationName: String,
    val vehicleIdNum: String,
    val shouldBeUploaded: Boolean,
    val detail: String?
)