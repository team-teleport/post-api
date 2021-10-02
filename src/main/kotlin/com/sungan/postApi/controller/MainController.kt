package com.sungan.postApi.controller

import com.sungan.postApi.dto.GetMainRequestDto
import com.sungan.postApi.service.SunganService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["MainController - 메인 피드 관련 API"])
@RequestMapping("main")
class MainController(
    val sunganService: SunganService
) {

    @GetMapping("")
    @ApiOperation(value = "메인의 순간 가져오기 API")
    fun getMain(@ApiIgnore userId: Long, @RequestBody getMainRequestDto: GetMainRequestDto) =
        sunganService.readMainSungans(userId, getMainRequestDto)
}