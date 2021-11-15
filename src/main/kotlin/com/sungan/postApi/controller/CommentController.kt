package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.CommentVo
import com.sungan.postApi.dto.PatchCommentRequestDto
import com.sungan.postApi.dto.PostCommentRequestDto
import com.sungan.postApi.dto.PostNestedCommentReqDto
import com.sungan.postApi.service.CommentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiModelProperty
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("comment")
@Api(tags = ["댓글 관련 API"])
class CommentController(
    val commentService: CommentService
) {
    @PostMapping("")
    @ApiModelProperty(value = "댓글 생성하기 API")
    fun postComment(@ApiIgnore userId: Long, @RequestBody postCommentRequestDto: PostCommentRequestDto): SunganResponse<CommentVo> =
        SunganResponse(commentService.createComment(userId, postCommentRequestDto))

    @DeleteMapping("/{id}")
    @ApiModelProperty(value = "댓글 삭제하기 API")
    fun deleteComment(@ApiIgnore userId: Long, @PathVariable(value = "id") commentId: Long): SunganResponse<CommentVo> =
        SunganResponse(commentService.destroyComment(userId, commentId))

    @PatchMapping("")
    @ApiModelProperty(value = "댓글 수정하기 API")
    fun patchComment(
        @ApiIgnore userId: Long,
        @RequestBody patchCommentRequestDto: PatchCommentRequestDto
    ): SunganResponse<CommentVo> =
        SunganResponse(commentService.updateComment(userId, patchCommentRequestDto))

    @PostMapping("/{id}/reply")
    @ApiModelProperty(value = "대댓글 달기 API")
    fun postNestedComment(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") commentId: Long,
        @RequestBody postNestedCommentReqDto: PostNestedCommentReqDto
    ): SunganResponse<Any> {
        commentService.createNestedComment(userId, commentId, postNestedCommentReqDto)
        return SunganResponse(HttpStatus.OK, "대댓글 생성 완료")
    }

    @DeleteMapping("/reply/{id}")
    @ApiModelProperty(value = "대댓글 삭제하기 API")
    fun deleteNestedComment(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") nestedCommentId: Long,
    ): SunganResponse<Any> {
        commentService.destroyNestedComment(userId, nestedCommentId)
        return SunganResponse(HttpStatus.OK, "순간 대댓글 삭제 성공")
    }
}