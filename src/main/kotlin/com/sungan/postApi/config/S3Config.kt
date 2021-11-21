package com.sungan.postApi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient

@Component
class S3Config(
    @Value("\${cloud.aws.credentials.access-key}")
    val accessKey: String,

    @Value("\${cloud.aws.credentials.secret-key}")
    val secretKey: String,

    @Value("\${cloud.aws.region.static}")
    val region: String
) {
    @Bean
    fun awsS3AsyncClient(): S3AsyncClient {
        val awsCredential = AwsBasicCredentials.create(accessKey, secretKey)
        val region = Region.of(region)
        return S3AsyncClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredential))
            .region(region)
            .build()
    }
}