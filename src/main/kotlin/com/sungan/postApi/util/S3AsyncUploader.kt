package com.sungan.postApi.util

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

@Component
class S3AsyncUploader(
    private val s3AsyncClient: S3AsyncClient,
    @Value("\${cloud.aws.s3.bucket}") private val bucketName: String,
) {

    private val logger = KotlinLogging.logger {  }

    fun putObject(key: String, multipartFile: MultipartFile): CompletableFuture<PutObjectResponse> {
        val file = convertMultipartToFile(multipartFile)
        val objectRequest = PutObjectRequest
            .builder().bucket(bucketName).key(key).build()
        return s3AsyncClient.putObject(objectRequest, AsyncRequestBody.fromFile(Path.of(file.path)))
            .whenComplete { _, _ -> removeNewFile(file) }
    }

    fun generateKey(fileName: String): String {
        return "sungan/img/$fileName"
    }

    fun generateObjectUrl(key: String): String {
        return "https://$bucketName.s3.ap-northeast-2.amazonaws.com/$key"
    }

    fun convertMultipartToFile(multipartFile: MultipartFile): File {
        val convertedFile = File(System.getProperty("user.dir") + "/" + multipartFile.originalFilename)
        if (convertedFile.createNewFile()) {
            val fos = FileOutputStream(convertedFile)
            fos.write(multipartFile.bytes)
            return convertedFile
        }
        throw IllegalStateException("파일 변환 실패")
    }

    fun removeNewFile(targetFile: File) {
        if (targetFile.delete()) {
            logger.info("사진 삭제 완료")
            return
        }
        logger.info("$targetFile 삭제 실패")
    }
}