package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.PostReportReqDto
import com.sungan.postApi.dto.ReportVo
import com.sungan.postApi.service.ReportService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["Report 관련 API"])
@RequestMapping("/report")
class ReportController(
    val reportService: ReportService
) {
    @PostMapping("")
    @ApiOperation(value = "신고하기")
    fun postReport(
        @ApiIgnore userId: Long,
        @RequestBody postReportReqDto: PostReportReqDto
    ): SunganResponse<ReportVo> {
        return SunganResponse(reportService.createReport(userId, postReportReqDto))
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "신고 자세히 보기")
    fun getReport(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") reportId: Long
    ): SunganResponse<ReportVo> {
        return SunganResponse(reportService.readReport(userId, reportId))
    }
}