package com.sungan.postApi.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.dto.UserInfoReqDto
import com.sungan.postApi.dto.UserInfoResDto
import mu.KotlinLogging
import okhttp3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UserInfoUtil(
    @Value("\${auth-api.key}")
    private val apiKey: String,
    @Value("\${auth-api.user-info.url}")
    private val urlStr: String,
) {
    private val logger = KotlinLogging.logger {  }
    private val client: OkHttpClient = OkHttpClient()
    private val objectMapper = ObjectMapper()

    fun getUserInfo(userId: Long): UserInfoResDto {
        val url = HttpUrl.parse(urlStr)!!.newBuilder().build()
        val reqDto = UserInfoReqDto(userId)
        val requestBody = Gson().toJson(reqDto).toString()
        val request: Request = Request.Builder()
            .url(url).addHeader("Authorization", apiKey)
            .post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), requestBody))
            .build()
        val response = try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            logger.error(e.message)
            throw SunganException(SunganError.UNKNOWN_ERROR)
        }
        return try {
            objectMapper.readValue(response.body()?.string(), UserInfoResDto::class.java)
        } catch (e: Exception) {
            logger.error(e.message)
            throw SunganException(SunganError.UNEXPECTED_VALUE)
        }
    }
}