package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.GetAllPostReqDto
import com.sungan.postApi.dto.GetMainRequestDto
import com.sungan.postApi.dto.GetSunganByChannelReqDto
import com.sungan.postApi.dto.PostBaseWithLikeByUserAndBestComment
import com.sungan.postApi.service.HotplaceService
import com.sungan.postApi.service.MainService
import com.sungan.postApi.service.ReportService
import com.sungan.postApi.service.SunganService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@Api(tags = ["메인 피드 관련 API"])
@RequestMapping("/main")
class MainController(
    private val hotplaceService: HotplaceService,
    private val reportService: ReportService,
    private val sunganService: SunganService,
    private val mainService: MainService
) {

    @PostMapping("")
    fun getSungansAfter(
        @ApiIgnore userId: Long,
        @RequestBody getAllPostReqDto: GetAllPostReqDto,
        @ApiParam(required = false) @RequestParam(required = false) stationName: String?
    ): SunganResponse<List<PostBaseWithLikeByUserAndBestComment>> =
        SunganResponse(mainService.readMainSungans(userId, getAllPostReqDto, stationName))

    @PostMapping("/sungans")
    fun getSungansAfter(
        @ApiIgnore userId: Long,
        @RequestBody getSunganByChannelReqDto: GetSunganByChannelReqDto,
        @ApiParam(required = false) @RequestParam(required = false) stationName: String?
    ): SunganResponse<List<PostBaseWithLikeByUserAndBestComment>> {
        val res = sunganService.readSungans(userId, getSunganByChannelReqDto, stationName)
        return SunganResponse(res)
    }

    @PostMapping("/places")
    fun getHotplacesAfter(
        @ApiIgnore userId: Long,
        @RequestBody getMainRequestDto: GetMainRequestDto,
        @ApiParam(required = false) @RequestParam(required = false) stationName: String?
    ): SunganResponse<List<PostBaseWithLikeByUserAndBestComment>> {
        val res = hotplaceService.readHotplace(userId, getMainRequestDto, stationName)
        return SunganResponse(res)
    }

    @PostMapping("/reports")
    fun getReportsAfter(
        @ApiIgnore userId: Long,
        @RequestBody getMainRequestDto: GetMainRequestDto,
        @ApiParam(required = false) @RequestParam(required = false) stationName: String?
    ): SunganResponse<List<PostBaseWithLikeByUserAndBestComment>> {
        val res = reportService.readReports(userId, getMainRequestDto, stationName)
        return SunganResponse(res)
    }

    @GetMapping("")
    fun getSunganMain(
        @ApiIgnore userId: Long,
        @ApiParam(required = false) @RequestParam(required = false) stationName: String?
    ): SunganResponse<List<PostBaseWithLikeByUserAndBestComment>> {
        return SunganResponse(mainService.getMainList(userId, stationName))
    }
}