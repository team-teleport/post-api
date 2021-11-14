package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.HotplaceComment
import com.sungan.postApi.domain.hotplace.HotplaceCommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceCommentLikeRepository: JpaRepository<HotplaceCommentLike, Long>, HotplaceCommentLikeQueryRepository {
    fun findByHotplaceCommentAndUserId(hotplaceComment: HotplaceComment, userId: Long): HotplaceCommentLike?
    fun deleteAllBy(hotplaceComment: HotplaceComment)
}