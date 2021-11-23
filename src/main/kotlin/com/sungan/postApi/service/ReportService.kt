package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.report.*
import com.sungan.postApi.domain.report.Report
import com.sungan.postApi.dto.*
import com.sungan.postApi.event.publisher.NotiEventPublisher
import com.sungan.postApi.event.publisher.NotiType
import com.sungan.postApi.event.publisher.PostType
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReportService(
    val reportRepository: ReportRepository,
    val reportCommentRepository: ReportCommentRepository,
    val reportCommentLikeRepository: ReportCommentLikeRepository,
    val reportNestedCommentRepository: ReportNestedCommentRepository,
    val reportLikeRepository: ReportLikeRepository,
    val reportTypeRepository: ReportTypeRepository,
    val notiEventPublisher: NotiEventPublisher,
) {
    fun createReport(userId: Long, postReportReqDto: PostReportReqDto): ReportVo {
        val type = reportTypeRepository.findByLabel(postReportReqDto.label)
        val report = reportRepository.save(
            Report(
                type ?: throw SunganException(SunganError.BAD_REQUEST, "해당 label이 없습니다"),
                postReportReqDto.makeUserInfo(userId),
                postReportReqDto.shouldBeUploaded,
                postReportReqDto.vehicleIdNum,
                postReportReqDto.detail
            )
        )
        return report.convertToVo()
    }

    fun readReport(userId: Long, reportId: Long): ReportVo {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        if (!report.shouldBeUploaded) throw SunganException(SunganError.BAD_REQUEST) // 업로드 되지 않는 신고글일 경우
        report.readCnt += 1
        return report.convertToVo()
    }

    @Transactional
    fun updateReport(userId: Long, updateReportReqDto: UpdateReportReqDto): ReportVo {
        val report = reportRepository.findById(updateReportReqDto.id).orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (report.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        if (updateReportReqDto.detail != null) report.detail = updateReportReqDto.detail
        if (updateReportReqDto.vehicleIdNum != null) report.vehicleNum = updateReportReqDto.vehicleIdNum
        if (updateReportReqDto.shouldBeUploaded != null) {
            report.shouldBeUploaded = updateReportReqDto.shouldBeUploaded
        }
        return report.convertToVo()
    }

    fun destroyReport(userId: Long, reportId: Long) {
        val report = reportRepository.findById(reportId).orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (report.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        reportRepository.delete(report)
    }

    // TODO: Comment 관련 메서드 전부 reportCommentService 만들어서 분리
    fun createReportComment(userId: Long, postReportCommentReqDto: PostReportCommentReqDto) {
        val report = reportRepository.findById(postReportCommentReqDto.reportId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val newComment = reportCommentRepository.save(
            ReportComment(
                postReportCommentReqDto.content,
                report,
                postReportCommentReqDto.makeUserInfo(userId)
            )
        )
        if (userId != report.userInfo.userId) {
            notiEventPublisher.publishCommentRegisteredEvent(
                report.userInfo.userId,
                newComment.userInfo.userName,
                NotiType.Comment,
                PostType.Report,
                report.id!!,
                newComment.id
            )
        }
    }

    fun destroyReportComment(userId: Long, reportCommentId: Long) {
        val reportComment = reportCommentRepository.findById(reportCommentId)
            .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (reportComment.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        reportCommentRepository.delete(reportComment)
    }

    fun updateReportComment(
        userId: Long,
        reportCommentId: Long,
        content: String
    ) {
        val reportComment = reportCommentRepository.findById(reportCommentId)
            .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (reportComment.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        reportComment.content = content
    }

    fun readReportCommentsWithLikes(
        userId: Long,
        reportId: Long
    ): List<CommentWithLikeCntAndIsLiked<ReportNestedCommentVo>> {
        val report =
            reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (!report.shouldBeUploaded) throw SunganException(SunganError.BAD_REQUEST)
        val comments = reportCommentRepository.findByReport(report)
        return comments.asSequence().map { comment ->
            val likeCnt = reportCommentLikeRepository.countByReportComment(comment)
            CommentWithLikeCntAndIsLiked(
                comment.id!!,
                comment.content,
                comment.userInfo,
                comment.createdAt,
                comment.updatedAt,
                likeCnt,
                reportCommentLikeRepository.findByReportCommentAndUserId(comment, userId) != null,
                comment.nestedComments.map { nestedComment -> nestedComment.convertToVo() }
            )
        }.toList()
    }

    fun createNestedComment(userId: Long, postReportNestedCommentReqDto: PostReportNestedCommentReqDto) {
        val comment = reportCommentRepository.findById(postReportNestedCommentReqDto.commentId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        if (!comment.report.shouldBeUploaded) throw SunganException(SunganError.BAD_REQUEST) // 업로드 되지 않은 신고글일경우
        val newNestedComment = reportNestedCommentRepository.save(
            ReportNestedComment(
                comment,
                postReportNestedCommentReqDto.content,
                postReportNestedCommentReqDto.makeUserInfo(userId)
            )
        )
        if (comment.report.userInfo.userId != userId) {
            notiEventPublisher.publishCommentRegisteredEvent(
                comment.report.userInfo.userId,
                newNestedComment.userInfo.userName,
                NotiType.Comment,
                PostType.Report,
                comment.report.id!!,
                newNestedComment.id
            )
        }
        if (comment.userInfo.userId != userId) {
            notiEventPublisher.publishNestedCommentRegisteredEvent(
                comment.userInfo.userId,
                newNestedComment.userInfo.userName,
                NotiType.Comment,
                PostType.Report,
                comment.report.id!!,
                newNestedComment.id
            )
        }
    }

    fun destroyNestedComment(userId: Long, nestedCommentId: Long) {
        val nestedComment = reportNestedCommentRepository.findById(nestedCommentId)
            .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (nestedComment.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        reportNestedCommentRepository.delete(nestedComment)
    }

    fun updateNestedComment(userId: Long, nestedCommentId: Long, content: String) {
        val nestedComment = reportNestedCommentRepository.findById(nestedCommentId)
            .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND) }
        if (nestedComment.userInfo.userId != userId) throw SunganException(SunganError.FORBIDDEN)
        nestedComment.content = content
    }

    fun createReportCommentLike(userId: Long, commentId: Long) {
        val reportComment =
            reportCommentRepository.findById(commentId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        reportCommentLikeRepository.findByReportCommentAndUserId(reportComment, userId)?.let {
            throw SunganException(SunganError.DUPLICATE, "이미 좋아요한 댓글입니다.")
        }
        val newComment = reportCommentLikeRepository.save(
            ReportCommentLike(
                reportComment,
                userId
            )
        )
        if (userId != reportComment.userInfo.userId) {
            notiEventPublisher.publishLikeRegisteredEvent(
                reportComment.userInfo.userId,
                userId,
                NotiType.Comment,
                PostType.Report,
                reportComment.report.id!!,
                newComment.id
            )
        }
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
        if (userId != report.userInfo.userId) {
            notiEventPublisher.publishLikeRegisteredEvent(
                report.userInfo.userId,
                userId,
                NotiType.Post,
                PostType.Report,
                reportId
            )
        }
    }

    fun destroyReportLike(userId: Long, reportId: Long) {
        val report = reportRepository.findById(reportId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val reportLike =
            reportLikeRepository.findByReportAndUserId(report, userId) ?: throw SunganException(SunganError.BAD_REQUEST)
        reportLikeRepository.delete(reportLike)
    }
}