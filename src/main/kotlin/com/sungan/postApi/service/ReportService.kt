package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Report
import com.sungan.postApi.domain.ReportComment
import com.sungan.postApi.dto.*
import com.sungan.postApi.repository.Line2StationRepository
import com.sungan.postApi.repository.ReportCommentLikeRepository
import com.sungan.postApi.repository.ReportCommentRepository
import com.sungan.postApi.repository.ReportRepository
import org.springframework.stereotype.Service

@Service
class ReportService(
    val reportRepository: ReportRepository,
    val stationRepository: Line2StationRepository,
    val reportCommentRepository: ReportCommentRepository,
    val reportCommentLikeRepository: ReportCommentLikeRepository
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
        if (!report.shouldBeUploaded) throw SunganException(SunganError.BAD_REQUEST) // 업로드 되지 않는 신고글일 경우
        return report.convertToVo()
    }

    fun createReportComment(userId: Long, postReportCommentReqDto: PostReportCommentReqDto) {
        val report = reportRepository.findById(postReportCommentReqDto.reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        reportCommentRepository.save(ReportComment(
            postReportCommentReqDto.content,
            report,
            userId
        ))
    }

    fun readReportCommentsWithLikes(userId: Long, reportId: Long): List<ReportCommentWithLike> {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val comments = reportCommentRepository.findByReport(report)
        return comments.asSequence().map { comment ->
            val likeCnt = reportCommentLikeRepository.countByReportComment(comment)
            ReportCommentWithLike(
                comment.content,
                comment.userId,
                comment.createdAt,
                comment.updatedAt,
                likeCnt,
                reportCommentLikeRepository.findByReportCommentAndUserId(comment, userId) != null
            )
        }.toList()
    }
}