package com.sungan.postApi.dto

import com.sungan.postApi.domain.ReportType
import com.sungan.postApi.domain.VehicleType

class Report

data class ReportVo(
    var id: Long?,
    var reportType: ReportType,
    var userId: Long,
    var detail: String?,
    var vehicleType: VehicleType,
    var vehicleIdNum: String,
    var readCnt: Long,
    var likeCnt: Long
)

data class PostReportReqDto(
    val reportType: ReportType,
    val vehicleType: VehicleType,
    val vehicleIdNum: String,
    val detail: String?
)