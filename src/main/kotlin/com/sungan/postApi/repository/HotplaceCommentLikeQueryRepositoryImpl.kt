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
        val hotplaceComments = queryFactory
            .selectFrom(hotplaceComment).join(hotplace)
            .on(hotplace.eq(hotplaceComment.hotplace).and(hotplace.eq(hot)))
            .fetch()

        queryFactory.delete(hotplaceCommentLike).where(hotplaceCommentLike.hotplaceComment.`in`(hotplaceComments))
            .execute()
    }
}