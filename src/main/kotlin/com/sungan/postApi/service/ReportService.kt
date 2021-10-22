package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Report
import com.sungan.postApi.dto.PostReportReqDto
import com.sungan.postApi.dto.ReportVo
import com.sungan.postApi.repository.ReportRepository
import org.springframework.stereotype.Service

@Service
class ReportService(
    val reportRepository: ReportRepository
) {
    fun createReport(userId: Long, postReportReqDto: PostReportReqDto): ReportVo {
        val report = reportRepository.save(
            Report(
                postReportReqDto.reportType,
                postReportReqDto.vehicleType,
                postReportReqDto.vehicleIdNum,
                userId,
                postReportReqDto.detail,
            )
        )
        return report.toVo()
    }

    fun readReport(userId: Long, reportId: Long): ReportVo {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        return report.toVo()
    }
}