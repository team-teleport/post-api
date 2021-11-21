package com.sungan.postApi.event.publisher

import com.sungan.postApi.dto.Goto
import com.sungan.postApi.dto.NotificationReqDto
import com.sungan.postApi.event.NotiRegisteredEvent
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NotiEventPublisher(
    private val eventPublisher: ApplicationEventPublisher,
) {
    private val logger = KotlinLogging.logger {  }
    @Async
    fun publishCommentRegisteredEvent(authorId: Long, commentUserNickname: String) {
        val notiReq = NotificationReqDto(
            mutableListOf(authorId),
            "순간이동",
            "${commentUserNickname}이 댓글을 남겼습니다.",
            Goto("Home")
        )
        eventPublisher.publishEvent(NotiRegisteredEvent(notiReq))
        logger.info("published comment registered event to user ${authorId}.")
    }

    @Async
    fun publishNestedCommentRegisteredEvent(authorId: Long, commentUserNickname: String) {
        val notiReq = NotificationReqDto(
            mutableListOf(authorId),
            "순간이동",
            "${commentUserNickname}이 대댓글을 남겼습니다.",
            Goto("Home")
        )
        eventPublisher.publishEvent(NotiRegisteredEvent(notiReq))
        logger.info("published comment registered event to user ${authorId}.")
    }

    @Async
    fun publishLikeRegisteredEvent(authorId: Long, LikedUserNickname: String, likeType: LikeType) {
        val likedThing = when (likeType) {
            LikeType.Post -> "게시글"
            LikeType.Comment -> "댓글"
        }
        val notiReq = NotificationReqDto(
            mutableListOf(authorId),
            "순간이동",
            "${LikedUserNickname}이 회원님의 ${likedThing}을 좋아합니다.",
            Goto("Home")
        )
        eventPublisher.publishEvent(NotiRegisteredEvent(notiReq))
        logger.info("published comment registered event to user ${authorId}.")
    }
}

enum class LikeType {
    Post,
    Comment
}