package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.QHotplace.hotplace
import com.sungan.postApi.domain.hotplace.QHotplaceComment.hotplaceComment
import com.sungan.postApi.domain.hotplace.QHotplaceCommentLike.hotplaceCommentLike

class HotplaceCommentLikeQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : HotplaceCommentLikeQueryRepository {
    override fun deleteAllByHotplace(hot: Hotplace) {
        val hotplaceCommentIds = queryFactory.select(hotplaceComment.id)
            .from(hotplaceComment).join(hotplace).on(hotplace.eq(hotplaceComment.hotplace))
            .where(hotplaceComment.hotplace.eq(hot))
        queryFactory.delete(hotplaceCommentLike).where(hotplaceCommentLike.hotplaceComment.id.`in`(hotplaceCommentIds))
    }
}