package com.sooni.postapi.controller

import com.sooni.postapi.application.support.SunganResponse
import com.sooni.postapi.dto.CommentVo
import com.sooni.postapi.dto.PatchCommentRequestDto
import com.sooni.postapi.dto.PostCommentRequestDto
import com.sooni.postapi.service.CommentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiModelProperty
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("comment")
@Api(tags = ["CommentController - 댓글 관련 API"])
class CommentController(
    val commentService: CommentService
) {
    @PostMapping("")
    @ApiModelProperty(value = "댓글 생성하기 API")
    fun postComment(userId: Long, @RequestBody postCommentRequestDto: PostCommentRequestDto): SunganResponse<CommentVo> =
        SunganResponse(commentService.createComment(userId, postCommentRequestDto))

    @DeleteMapping("/{id}")
    @ApiModelProperty(value = "댓글 삭제하기 API")
    fun deleteComment(userId: Long, @PathVariable(value = "id") commentId: Long): SunganResponse<CommentVo> =
        SunganResponse(commentService.destroyComment(userId, commentId))

    @PatchMapping("")
    @ApiModelProperty(value = "댓글 수정하기 API")
    fun patchComment(
        userId: Long,
        @RequestBody patchCommentRequestDto: PatchCommentRequestDto
    ): SunganResponse<CommentVo> =
        SunganResponse(commentService.updateComment(userId, patchCommentRequestDto))
}