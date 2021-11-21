package com.sungan.postApi.dto

data class NotificationReqDto(
    val userIds: MutableList<Long>,
    val title: String,
    val body: String,
    val data: Goto,
)


data class Goto(
    val goto: String, // "Post"
    val notiType: String, // 알림 누르면 이동해야하는 곳: "Post", "Comment"
    val postType: String, // "Sungan", "Hotplace", "Report"
    val postId: Long? = null,
    val commentId: Long? = null,
)