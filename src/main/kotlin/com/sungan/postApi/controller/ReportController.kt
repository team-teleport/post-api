package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.*
import com.sungan.postApi.service.ReportService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
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

    @DeleteMapping("/{id}")
    @ApiOperation(value = "민원 삭제하기")
    fun deleteReport(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") reportId: Long
    ): SunganResponse<Any> {
        reportService.destroyReport(userId, reportId)
        return SunganResponse(HttpStatus.OK, "민원 삭제 성공")
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "신고 자세히 보기")
    fun getReport(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") reportId: Long
    ): SunganResponse<ReportVo> {
        return SunganResponse(reportService.readReport(userId, reportId))
    }

    @PostMapping("/comment")
    @ApiOperation(value = "신고에 댓글 달기")
    fun postReportComment(
        @ApiIgnore userId: Long,
        @RequestBody postReportCommentReqDto: PostReportCommentReqDto
    ): SunganResponse<Any> {
        reportService.createReportComment(userId, postReportCommentReqDto)
        return SunganResponse(HttpStatus.OK, "댓글 달기 성공")
    }

    @DeleteMapping("/comment/{id}")
    @ApiOperation(value = "민원 댓글 삭제하기")
    fun deletePlaceComment(
        @ApiIgnore userId: Long,
        @ApiParam(value = "민원 id") @PathVariable(value = "id") reportId: Long
    ): SunganResponse<Any> {
        reportService.destroyReportComment(userId, reportId)
        return SunganResponse(HttpStatus.OK, "민원 댓글 삭제 성공")
    }
    @GetMapping("/{id}/comments")
    @ApiOperation(value = "신고글의 모든 댓글 보기")
    fun getAllComments(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") reportId: Long
    ): SunganResponse<List<CommentWithLikeCntAndIsLiked<ReportNestedCommentVo>>> {
        return SunganResponse(reportService.readReportCommentsWithLikes(userId, reportId))
    }

    @PostMapping("/{id}/like")
    @ApiOperation(value = "민원 좋아요하기")
    fun postReportLike(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") reportId: Long
    ): SunganResponse<Any> {
        reportService.createReportLike(userId, reportId)
        return SunganResponse(HttpStatus.OK, "민원 좋아요 성공")
    }

    @DeleteMapping("/{id}/like")
    @ApiOperation(value = "민원 좋아요 취소하기")
    fun deleteReportLike(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") reportId: Long
    ): SunganResponse<Any> {
        reportService.destroyReportLike(userId, reportId)
        return SunganResponse(HttpStatus.OK, "민원 좋아요 취소 성공")
    }

    @PostMapping("/comment/reply")
    @ApiOperation(value = "신고글 댓글에 대댓글 달기")
    fun postReportNestedComment(
        @ApiIgnore userId: Long,
        @RequestBody postReportNestedCommentReqDto: PostReportNestedCommentReqDto
    ): SunganResponse<Any> {
        reportService.createNestedComment(userId, postReportNestedCommentReqDto)
        return SunganResponse(HttpStatus.OK, "신고글 대댓글 달기 성공")
    }

    @DeleteMapping("/comment/reply/{id}")
    @ApiOperation(value = "민원글 대댓글 삭제하기")
    fun deleteReportNestedComment(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") nestedCommentId: Long
    ): SunganResponse<Any> {
        reportService.destroyNestedComment(userId, nestedCommentId)
        return SunganResponse(HttpStatus.OK, "민원 대댓글 삭제 성공")
    }

    @PostMapping("/comment/{id}/like")
    @ApiOperation(value = "댓글에 좋아요 누르기")
    fun postReportCommentLike(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") commentId: Long
    ): SunganResponse<Any> {
        reportService.createReportCommentLike(userId, commentId)
        return SunganResponse(HttpStatus.OK, "댓글 좋아요 성공")
    }

    @DeleteMapping("/comment/like/{id}")
    @ApiOperation(value = "댓글 좋아요 취소하기")
    fun deleteReportCommentLike(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") likeId: Long
    ): SunganResponse<Any> {
        reportService.destroyReportCommentLike(userId, likeId)
        return SunganResponse(HttpStatus.OK, "좋아요 취소 성공")
    }
}