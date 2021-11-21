package com.sungan.postApi.event

import com.sungan.postApi.dto.NotificationReqDto

class NotiRegisteredEvent(
    val notiReq: NotificationReqDto,
    val notiType: String? = null,
    val postId: Long? = null,
)