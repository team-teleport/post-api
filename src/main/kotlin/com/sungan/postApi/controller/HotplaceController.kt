package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.HotplaceCommentVo
import com.sungan.postApi.dto.HotplaceVo
import com.sungan.postApi.dto.PostHotplaceReqDto
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
        userId: Long,
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
        SunganResponse(hotplaceCommentService.readHotplaceCommentList(userId, id))
    }
}