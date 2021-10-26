package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.dto.*
import com.sungan.postApi.service.MainService
import com.sungan.postApi.service.SunganService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["순간(일반 글) 관련 API"])
@RequestMapping("sungan")
class SunganController(
    val sunganService: SunganService,
    val mainService: MainService
) {
    @GetMapping("/{id}")
    @ApiOperation(value = "순간 읽기 API")
    fun getSungan(
        @ApiIgnore userId: Long,
        @ApiParam(example = "1") @PathVariable(name = "id", required = true) id: Long
    ) = SunganResponse(sunganService.readSunganById(ReadSunganDto(userId, id)))

    @PostMapping("")
    @ApiOperation(value = "순간 생성하기 API")
    fun postSungan(@ApiIgnore userId: Long, @RequestBody getSunganRequestDto: CreateSunganRequestDto): SunganResponse<SunganDto> =
        SunganResponse(sunganService.createSungan(userId, getSunganRequestDto))

    @PatchMapping("")
    @ApiOperation(value = "순간 수정하기 API")
    fun patchSungan(
        @ApiIgnore userId: Long,
        @RequestBody patchSunganRequestDto: PatchSunganRequestDto
    ): SunganResponse<SunganDto> =
        SunganResponse(sunganService.updateSungan(userId, patchSunganRequestDto))

    @DeleteMapping("/{id}")
    @ApiOperation(value = "순간 삭제하기 API")
    fun deleteSungan(@ApiIgnore userId: Long, @PathVariable(value = "id") sunganId: Long): SunganResponse<SunganVo> {
        return SunganResponse(sunganService.destroySungan(userId, sunganId))
    }

    @GetMapping("/my")
    @ApiOperation(value = "내가 쓴 글 가져오기 API")
    fun getMySungans(
        @ApiIgnore userId: Long
    ): SunganResponse<List<PostBaseEntity>> {
        return SunganResponse(mainService.getMySunganList(userId))
    }
}