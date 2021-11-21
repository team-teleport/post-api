package com.sungan.postApi.dto

data class NotificationReqDto(
    val userIds: MutableList<Long>,
    val title: String,
    val body: String,
    val data: Goto,
)

data class Goto(
    val goto: String
)