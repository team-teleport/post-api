package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.HotplaceComment
import com.sungan.postApi.domain.hotplace.HotplaceCommentLike
import com.sungan.postApi.repository.query.HotplaceCommentLikeQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceCommentLikeRepository: JpaRepository<HotplaceCommentLike, Long>, HotplaceCommentLikeQueryRepository {
    fun findByHotplaceCommentAndUserId(hotplaceComment: HotplaceComment, userId: Long): HotplaceCommentLike?
}