package com.sungan.postApi.controller

import com.sungan.postApi.dto.GetMainRequestDto
import com.sungan.postApi.service.SunganService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("main")
class MainController(
    val sunganService: SunganService
) {

    @GetMapping("")
    fun getMain(userId: Long, getMainRequestDto: GetMainRequestDto) =
        sunganService.readMainSungans(userId, getMainRequestDto)
}