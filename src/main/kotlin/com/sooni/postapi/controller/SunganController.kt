package com.sooni.postapi.controller

import com.sooni.postapi.application.support.SunganResponse
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.*
import com.sooni.postapi.service.SunganService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*

@RestController
@Api(tags = ["SunganController - 순간 관련 API"])
@RequestMapping("sungan")
class SunganController(
    val sunganService: SunganService
) {
    @GetMapping("/{id}")
    @ApiOperation(value = "순간 읽기 API")
    fun getSungan(
        user: User?,
        @ApiParam(example = "1") @PathVariable(name = "id", required = true) id: Long
    ) = SunganResponse(sunganService.readSunganById(ReadSunganDto(user, id)))

    @PostMapping("")
    @ApiOperation(value = "순간 생성하기 API")
    fun postSungan(user: User, @RequestBody getSunganRequestDto: CreateSunganRequestDto): SunganResponse<SunganDto> =
        SunganResponse(sunganService.createSungan(user, getSunganRequestDto))

    @PatchMapping("")
    @ApiOperation(value = "순간 수정하기 API")
    fun patchSungan(user: User, @RequestBody patchSunganRequestDto: PatchSunganRequestDto): SunganResponse<SunganDto> =
        SunganResponse(sunganService.updateSungan(user, patchSunganRequestDto))

    @DeleteMapping("/{id}")
    @ApiOperation(value = "순간 삭제하기 API")
    fun deleteSungan(user: User, @PathVariable(value = "id") sunganId: Long): SunganResponse<SunganVo> {
        return SunganResponse(sunganService.destroySungan(user, sunganId))
    }
}