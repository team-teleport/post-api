package com.sungan.postApi.event.publisher

import com.sungan.postApi.dto.Goto
import com.sungan.postApi.dto.NotificationReqDto
import com.sungan.postApi.dto.UserInfoResDto
import com.sungan.postApi.event.NotiRegisteredEvent
import com.sungan.postApi.util.UserInfoUtil
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NotiEventPublisher(
    private val eventPublisher: ApplicationEventPublisher,
    private val userInfoUtil: UserInfoUtil,
) {
    private val logger = KotlinLogging.logger { }

    @Async
    fun publishCommentRegisteredEvent(
        authorId: Long,
        commentUserNickname: String,
        notiType: NotiType,
        postType: PostType,
        postId: Long,
        commentId: Long? = null
    ) {
        val notiReq = NotificationReqDto(
            mutableListOf(authorId),
            "순간이동",
            "${commentUserNickname}이 댓글을 남겼습니다.",
            Goto("Post", notiType.name, postType.name, postId, commentId)
        )
        eventPublisher.publishEvent(NotiRegisteredEvent(notiReq))
        logger.info("published comment registered event to user ${authorId}.")
    }

    @Async
    fun publishNestedCommentRegisteredEvent(
        authorId: Long,
        commentUserNickname: String,
        notiType: NotiType,
        postType: PostType,
        postId: Long,
        commentId: Long? = null
    ) {
        val notiReq = NotificationReqDto(
            mutableListOf(authorId),
            "순간이동",
            "${commentUserNickname}이 대댓글을 남겼습니다.",
            Goto("Post", notiType.name, postType.name, postId, commentId)
        )
        eventPublisher.publishEvent(NotiRegisteredEvent(notiReq))
        logger.info("published comment registered event to user ${authorId}.")
    }

    @Async
    fun publishLikeRegisteredEvent(
        authorId: Long,
        LikedUserId: Long,
        likeType: NotiType,
        postType: PostType,
        postId: Long,
        commentId: Long? = null
    ) {
        val likedUserInfo: UserInfoResDto = userInfoUtil.getUserInfo(LikedUserId)
        val likedThing = when (likeType) {
            NotiType.Post -> "게시글"
            NotiType.Comment -> "댓글"
        }
        val notiReq = NotificationReqDto(
            mutableListOf(authorId),
            "순간이동",
            "${likedUserInfo.username}이 회원님의 ${likedThing}을 좋아합니다.",
            Goto("Post", likeType.name, postType.name, postId, commentId)
        )
        eventPublisher.publishEvent(NotiRegisteredEvent(notiReq))
        logger.info("published comment registered event to user ${authorId}.")
    }
}

enum class NotiType { // 알림 누르면 이동하는 곳
    Post,
    Comment
}

enum class PostType {
    Sungan,
    Report,
    Hotplace,
}