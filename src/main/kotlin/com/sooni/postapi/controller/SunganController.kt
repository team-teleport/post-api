package com.sooni.postapi.controller

import com.sooni.postapi.application.support.SunganResponse
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.SunganDto
import com.sooni.postapi.service.SunganService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("sungan")
class SunganController(
    val sunganService: SunganService
) {
    @GetMapping("/{id}")
    fun getSungan(user: User, @PathVariable(name = "id") id: Long): SunganResponse<SunganDto> =
    fun getSungan(user: User?, @PathVariable(name = "id") id: Long): SunganResponse<SunganDto> =
        SunganResponse(sunganService.readSunganById(ReadSunganDto(user, id)))
}