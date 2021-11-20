package com.sungan.postApi.dto

import org.springframework.web.multipart.MultipartFile

data class ImageTestDto(
    val image: MultipartFile
)