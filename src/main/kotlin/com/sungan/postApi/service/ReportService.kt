package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Report
import com.sungan.postApi.dto.PostReportReqDto
import com.sungan.postApi.dto.ReportVo
import com.sungan.postApi.repository.Line2StationRepository
import com.sungan.postApi.repository.ReportRepository
import org.springframework.stereotype.Service

@Service
class ReportService(
    val reportRepository: ReportRepository,
    val stationRepository: Line2StationRepository
) {
    fun createReport(userId: Long, postReportReqDto: PostReportReqDto): ReportVo {
        val station =
            stationRepository.findByName(postReportReqDto.stationName) ?: throw SunganException(SunganError.BAD_REQUEST)
        val report = reportRepository.save(
            Report(
                postReportReqDto.reportType,
                station,
                userId,
                postReportReqDto.shouldBeUploaded,
                postReportReqDto.detail
            )
        )
        return report.convertToVo()
    }

    fun readReport(userId: Long, reportId: Long): ReportVo {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        return report.convertToVo()
    }
}