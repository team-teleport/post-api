package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.CommentLikeVo
import com.sungan.postApi.dto.PostCommentLike
import com.sungan.postApi.service.CommentLikeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/comment/like")
@Api(tags = ["댓글 좋아요 관련 API"])
class CommentLikeController(
    val commentLikeService: CommentLikeService
) {
    @PostMapping("")
    @ApiOperation(value = "댓글에 좋아요를 추가하는 API")
    fun postCommentLike(
        @ApiIgnore userId: Long,
        @RequestBody postCommentLike: PostCommentLike
    ): SunganResponse<CommentLikeVo> {
        return SunganResponse(commentLikeService.createCommentLike(userId, postCommentLike.commentId))
    }
}