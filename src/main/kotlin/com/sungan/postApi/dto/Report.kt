package com.sungan.postApi.dto

import com.sungan.postApi.domain.UserInfo
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

class Report

data class ReportVo(
    var id: Long?,
    var reportType: ReportTypeVo,
    var userInfo: UserInfo,
    var vehicleIdNum: String?,
    var detail: String?,
    var readCnt: Long,
    var likeCnt: Long,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
): PostBaseVo()

data class PostReportReqDto(
    val label: String,
    val vehicleIdNum: String? = null,
    val shouldBeUploaded: Boolean,
    val detail: String?,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

data class ReportCommentVo(
    val id: Long,
    val userInfo: UserInfo,
    val reportId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
): CommentBaseVo

data class ReportNestedCommentVo(
    val id: Long,
    val commentId: Long,
    val userInfo: UserInfo,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class PostReportCommentReqDto(
    val reportId: Long,
    val content: String,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

data class PatchReportCommentReqDto(
    val content: String
)

data class PostReportNestedCommentReqDto(
    val commentId: Long,
    val userId: Long,
    val content: String,
    override var userName: String,
    override var userProfileImgUrl: String?
): ReqIncludeUserInfo

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

data class ReportTypeVo(
    val id: Long,
    val category: String,
    val label: String
)