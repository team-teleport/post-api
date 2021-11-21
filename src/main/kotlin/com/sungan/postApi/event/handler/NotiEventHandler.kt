package com.sungan.postApi.event.handler

import com.google.gson.Gson
import com.sungan.postApi.event.NotiRegisteredEvent
import mu.KotlinLogging
import okhttp3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotiEventHandler {
    @Value("\${push-api.key}")
    private lateinit var apiKey: String
    private val client: OkHttpClient = OkHttpClient()
    private val pushUrl: String = "https://api.metasgid.com/api/v1/push"
    private val logger = KotlinLogging.logger {  }

    @EventListener
    fun push(event: NotiRegisteredEvent) {
        val json = Gson().toJson(event.notiReq)
        val body = RequestBody.create(MediaType.parse("application/json"), json)
        val url = HttpUrl
            .parse(pushUrl)!!
            .newBuilder()
            .build().toString()
        val request: Request = Request
            .Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $apiKey")
            .post(body)
            .build()
        val call: Call = client.newCall(request)
        val response: Response = call.execute()
        logger.info("Get response from Push server: ${response.body()!!.string()}")
        response.body()!!.close();
    }
}