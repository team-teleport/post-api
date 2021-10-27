package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.*
import com.sungan.postApi.domain.Report
import com.sungan.postApi.dto.*
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service

@Service
class ReportService(
    val reportRepository: ReportRepository,
    val reportCommentRepository: ReportCommentRepository,
    val reportCommentLikeRepository: ReportCommentLikeRepository,
    val reportNestedCommentRepository: ReportNestedCommentRepository,
    val reportLikeRepository: ReportLikeRepository
) {
    fun createReport(userId: Long, postReportReqDto: PostReportReqDto): ReportVo {
        val report = reportRepository.save(
            Report(
                postReportReqDto.reportType,
                postReportReqDto.makeUserInfo(userId),
                postReportReqDto.shouldBeUploaded,
                postReportReqDto.vehicleIdNum,
                postReportReqDto.carNum,
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
        val report = reportRepository.findById(postReportCommentReqDto.reportId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        reportCommentRepository.save(
            ReportComment(
                postReportCommentReqDto.content,
                report,
                postReportCommentReqDto.makeUserInfo(userId)
            )
        )
    }

    fun readReportCommentsWithLikes(userId: Long, reportId: Long): List<ReportCommentWithLike> {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        if (!report.shouldBeUploaded) throw SunganException(SunganError.BAD_REQUEST)
        val comments = reportCommentRepository.findByReport(report)
        return comments.asSequence().map { comment ->
            val likeCnt = reportCommentLikeRepository.countByReportComment(comment)
            ReportCommentWithLike(
                comment.content,
                comment.userInfo.userId,
                comment.createdAt,
                comment.updatedAt,
                likeCnt,
                reportCommentLikeRepository.findByReportCommentAndUserId(comment, userId) != null,
                comment.nestedComments.map { nestedComment ->
                    ReportNestedCommentVo(
                        nestedComment.id,
                        nestedComment.userInfo,
                        nestedComment.content,
                        nestedComment.createdAt,
                        nestedComment.updatedAt
                    )
                }
            )
        }.toList()
    }

    fun createNestedComment(userId: Long, postReportNestedCommentReqDto: PostReportNestedCommentReqDto) {
        val comment = reportCommentRepository.findById(postReportNestedCommentReqDto.commentId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        if (!comment.report.shouldBeUploaded) throw SunganException(SunganError.BAD_REQUEST) // 업로드 되지 않은 신고글일경우
        reportNestedCommentRepository.save(
            ReportNestedComment(
                comment,
                postReportNestedCommentReqDto.content,
                postReportNestedCommentReqDto.makeUserInfo(userId)
            )
        )
    }

    fun createReportCommentLike(userId: Long, commentId: Long) {
        val reportComment =
            reportCommentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        reportCommentLikeRepository.findByReportCommentAndUserId(reportComment, userId)?.let {
            throw SunganException(SunganError.DUPLICATE, "이미 좋아요한 댓글입니다.")
        }
        reportCommentLikeRepository.save(
            ReportCommentLike(
                reportComment,
                userId
            )
        )
    }

    fun destroyReportCommentLike(userId: Long, likeId: Long) {
        reportCommentLikeRepository.delete(
            reportCommentLikeRepository.findById(likeId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        )
    }

    fun createReportLike(userId: Long, reportId: Long) {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        reportLikeRepository.findByReportAndUserId(report, userId)
            ?.let { throw SunganException(SunganError.DUPLICATE, "이미 좋아요한 게시물입니다.") }
        reportLikeRepository.save(
            ReportLike(
                report, userId
            )
        )
    }

    fun destroyReportLike(userId: Long, reportId: Long) {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val reportLike =
            reportLikeRepository.findByReportAndUserId(report, userId) ?: throw SunganException(SunganError.BAD_REQUEST)
        reportLikeRepository.delete(reportLike)
    }
}