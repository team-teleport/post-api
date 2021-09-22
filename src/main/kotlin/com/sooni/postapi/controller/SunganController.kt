package com.sooni.postapi.controller

import com.sooni.postapi.application.support.SunganResponse
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.CreateSunganRequestDto
import com.sooni.postapi.dto.ReadSunganDto
import com.sooni.postapi.dto.SunganDto
import com.sooni.postapi.service.SunganService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@Api(value = "SunganController - 순간 관련 API")
@RequestMapping("sungan")
class SunganController(
    val sunganService: SunganService
) {
    @GetMapping("/{id}")
    @ApiOperation(value = "getSungan", notes = "순간 읽기 API")
    fun getSungan(user: User?, @PathVariable(name = "id") id: Long): SunganResponse<SunganDto> =
        SunganResponse(sunganService.readSunganById(ReadSunganDto(user, id)))

    @PostMapping("")
    @ApiOperation(value = "postSungan", notes = "순간 생성하기 API")
    fun postSungan(user: User, @RequestBody getSunganRequestDto: CreateSunganRequestDto): SunganResponse<SunganDto> =
        SunganResponse(sunganService.createSungan(user, getSunganRequestDto))

}