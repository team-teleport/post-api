package com.sungan.postApi.event.publisher

import com.sungan.postApi.dto.Goto
import com.sungan.postApi.dto.NotificationReqDto
import com.sungan.postApi.event.NotiRegisteredEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NotiEventPublisher(
    private val eventPublisher: ApplicationEventPublisher,
) {
    @Async
    fun publishCommentRegisteredEvent(authorId: Long, commentUserNickname: String) {
        val notiReq = NotificationReqDto(
            mutableListOf(authorId),
            "순간이동",
            "${commentUserNickname}이 댓글을 남겼습니다.",
            Goto("Home")
        )
        eventPublisher.publishEvent(NotiRegisteredEvent(notiReq))
    }
}