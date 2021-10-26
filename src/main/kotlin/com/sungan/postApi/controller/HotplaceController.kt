package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.*
import com.sungan.postApi.service.HotplaceCommentService
import com.sungan.postApi.service.HotplaceService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["핫플 관련 API"])
@RequestMapping("/place")
class HotplaceController(
    val hotplaceService: HotplaceService,
    val hotplaceCommentService: HotplaceCommentService
) {
    @PostMapping("")
    @ApiOperation(value = "핫플 올리기 API")
    fun postPlace(
        @ApiIgnore userId: Long,
        @RequestBody postHotplaceReqDto: PostHotplaceReqDto
    ): SunganResponse<Any> {
        hotplaceService.createHotplace(userId, postHotplaceReqDto)
        return SunganResponse(HttpStatus.OK, "핫플레이스 게시물 생성 성공")
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "핫플 상세보기 API")
    fun getPlace(
        @ApiIgnore userId: Long,
        @ApiParam(value = "핫플 id") @PathVariable(value = "id") id: Long
    ): SunganResponse<HotplaceWithLikeCommendCntVo> = SunganResponse(hotplaceService.readHotplace(userId, id))


    // TODO: 수정, 삭제

    @GetMapping("/{id}/comments")
    @ApiOperation(value = "핫플 댓글 전체 보기")
    fun getPlaceComments(
        @ApiIgnore userId: Long,
        @ApiParam(value = "핫플 id") @PathVariable(value = "id") id: Long
    ): SunganResponse<List<HotplaceCommentVo>> =
        SunganResponse(hotplaceCommentService.readHotplaceCommentList(userId, id)) // 대댓글까지 전부 보여줌

    @PostMapping("/comment")
    @ApiOperation(value = "핫플에 댓글 달기")
    fun postPlaceComment(
        @ApiIgnore userId: Long,
        @RequestBody postHotplaceCommentReqDto: PostHotplaceCommentReqDto
    ): SunganResponse<Any> {
        hotplaceCommentService.createHotplaceComment(userId, postHotplaceCommentReqDto)
        return SunganResponse(HttpStatus.OK, "핫플 댓글 달기 성공")
    }

    @PostMapping("/comment/reply")
    @ApiOperation(value = "핫플 댓글에 대댓글 달기")
    fun postPlaceNestedComment(
        @ApiIgnore userId: Long,
        @RequestBody postHotplaceNestedCommentReqDto: PostHotplaceNestedCommentReqDto
    ): SunganResponse<Any> {
        hotplaceCommentService.createHotplaceNestedComment(userId, postHotplaceNestedCommentReqDto)
        return SunganResponse(HttpStatus.OK, "핫플 대댓글 달기 성공")
    }

    @PostMapping("/comment/{id}/like")
    @ApiOperation(value = "핫플 댓글에 좋아요 누르기")
    fun postPlaceCommentLike(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") commentId: Long
    ): SunganResponse<Any> {
        hotplaceCommentService.createHotplaceCommentLike(userId, commentId)
        return SunganResponse(HttpStatus.OK, "핫플 댓글 좋아요 추가 성공")
    }

    @DeleteMapping("/comment/{id}/like")
    @ApiOperation(value = "핫플 댓글 좋아요 취소")
    fun deletePlaceCommentLike(
        @ApiIgnore userId: Long,
        @PathVariable(value = "id") commentId: Long
    ): SunganResponse<Any> {
        hotplaceCommentService.destroyHotplaceCommentLike(userId, commentId)
        return SunganResponse(HttpStatus.OK, "핫플 댓글 좋아요 취소 성공")
    }

}