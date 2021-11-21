package com.sungan.postApi.controller

import com.sungan.postApi.application.support.SunganResponse
import com.sungan.postApi.dto.ImageTestDto
import com.sungan.postApi.util.S3AsyncUploader
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@ApiIgnore
@RequestMapping("/test")
class TestController(
    private val s3AsyncUploader: S3AsyncUploader
) {
   @PostMapping("/img")
   fun uploadImage(
       @ModelAttribute imageTestDto: ImageTestDto
   ): ResponseEntity<String> {
       val key = s3AsyncUploader.generateKey(imageTestDto.image.originalFilename ?: "test-img")
       s3AsyncUploader.putObject(key, imageTestDto.image)
       return ResponseEntity(s3AsyncUploader.generateObjectUrl(key), HttpStatus.OK)
   }
}