package com.sooni.postapi.controller

import com.sooni.postapi.application.support.SunganResponse
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.CommentVo
import com.sooni.postapi.dto.PostCommentRequestDto
import com.sooni.postapi.service.CommentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiModelProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("comment")
@Api(tags = ["CommentController - 댓글 관련 API"])
class CommentController(
    val commentService: CommentService
) {
    @PostMapping("")
    @ApiModelProperty(value = "댓글 생성하기 API")
    fun postComment(user: User, @RequestBody postCommentRequestDto: PostCommentRequestDto): SunganResponse<CommentVo> =
        SunganResponse(commentService.createComment(user, postCommentRequestDto))
}