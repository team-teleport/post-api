package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.GetMainRequestDto
import com.sungan.postApi.dto.PostBaseWithLikeByUserAndBestComment
import com.sungan.postApi.dto.SunganWithLikeByUser
import com.sungan.postApi.service.MainService
import com.sungan.postApi.service.SunganService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["메인 피드 관련 API"])
@RequestMapping("/main")
class MainController(
    val sunganService: SunganService,
    val mainService: MainService
) {

    @ApiIgnore
    @PostMapping("/before")
    fun getSungansBefore(
        @ApiIgnore userId: Long,
        @ApiParam(value = "이전 피드의 첫번째 순간 아이디") @RequestParam(
            value = "firstId"
        ) id: Long,
        @RequestBody getMainRequestDto: GetMainRequestDto
    ): SunganResponse<List<SunganWithLikeByUser>> =
        SunganResponse(sunganService.readMainSungansBeforeId(firstSunganId = id, userId, getMainRequestDto))

    @ApiIgnore
    @PostMapping("/after")
    fun getSungansAfter(
        @ApiIgnore userId: Long,
        @ApiParam(value = "이전 피드의 마지막 순간 아이디, 처음 피드에 들어오는 경우 id를 비워주세요") @RequestParam(
            value = "lastId",
            required = false
        ) id: Long?,
        @RequestBody getMainRequestDto: GetMainRequestDto
    ): SunganResponse<List<SunganWithLikeByUser>> =
        SunganResponse(sunganService.readMainSungansAfterId(lastSunganId = id, userId, getMainRequestDto))

    @GetMapping("")
    fun getSunganMain(
        @ApiIgnore userId: Long,
        @ApiParam(required = false) @RequestParam(required = false) station: String?
    ): SunganResponse<List<PostBaseWithLikeByUserAndBestComment>> {
        return SunganResponse(mainService.getMainList(userId, station))
    }
}