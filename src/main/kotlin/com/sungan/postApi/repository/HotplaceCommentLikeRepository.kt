package com.sungan.postApi.repository

import com.sungan.postApi.domain.HotplaceComment
import com.sungan.postApi.domain.HotplaceCommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceCommentLikeRepository: JpaRepository<HotplaceCommentLike, Long> {
    fun findByHotplaceCommentAndUserId(hotplaceComment: HotplaceComment, userId: Long): HotplaceCommentLike?
}