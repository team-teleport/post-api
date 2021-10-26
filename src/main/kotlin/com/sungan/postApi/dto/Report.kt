package com.sungan.postApi.dto

import com.sungan.postApi.domain.ReportType
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

class Report

data class ReportVo(
    var id: Long?,
    var reportType: ReportType,
    var userId: Long,
    var detail: String?,
    var line2Station: Line2StationVo,
    var readCnt: Long,
    var likeCnt: Long,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
): PostBaseVo()

data class PostReportReqDto(
    val reportType: ReportType,
    val stationName: String,
    val vehicleIdNum: String,
    val shouldBeUploaded: Boolean,
    val detail: String?
)

data class ReportCommentVo(
    val id: Long,
    val userId: Long,
    val reportId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
): CommentBaseVo

data class ReportNestedCommentVo(
    val commentId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class PostReportCommentReqDto(
    val reportId: Long,
    val content: String,
)

data class PostReportNestedCommentReqDto(
    val commentId: Long,
    val userId: Long,
    val content: String
)

data class ReportCommentWithLikeList(
    val comments: List<ReportCommentWithLike>
)

data class ReportCommentWithLike(
    val content: String,
    val userId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val likeCnt: Long,
    @ApiModelProperty(value = "유저가 좋아요를 눌렀는지 안눌렀는지")
    val didLike: Boolean,
    val nestedComments: List<ReportNestedCommentVo>
)