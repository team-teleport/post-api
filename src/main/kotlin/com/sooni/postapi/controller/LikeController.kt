package com.sooni.postapi.controller

import com.sooni.postapi.application.support.SunganResponse
import com.sooni.postapi.dto.SunganLikeVo
import com.sooni.postapi.service.SunganLikeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("likes")
@Api(tags = ["LikeController - 순간 좋아요 관련 API"])
class LikeController(
    val sunganLikeService: SunganLikeService
) {

    @PostMapping("/{id}")
    @ApiOperation(value = "좋아요 추가하기 API")
    fun postSunganLike(@ApiIgnore userId: Long, @PathVariable(value = "id") sunganId: Long): SunganResponse<SunganLikeVo> =
        SunganResponse(sunganLikeService.createSunganLike(userId, sunganId))

    @DeleteMapping("/{id}")
    @ApiOperation(value = "좋아요 취소하기 API")
    fun deleteSunganLike(@ApiIgnore userId: Long, @PathVariable(value = "id") sunganId: Long): SunganResponse<Unit> {
        return SunganResponse(HttpStatus.OK, "좋아요 취소 성공")
    }
}