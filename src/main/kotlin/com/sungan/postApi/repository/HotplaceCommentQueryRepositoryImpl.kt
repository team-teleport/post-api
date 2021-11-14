package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceComment
import com.sungan.postApi.domain.hotplace.QHotplaceComment.hotplaceComment

class HotplaceCommentQueryRepositoryImpl(
    val query: JPAQueryFactory
) : HotplaceCommentQueryRepository {
    override fun findByHotplaceOrderByLikes(hotplace: Hotplace): HotplaceComment? {
        return query.selectFrom(hotplaceComment)
            .where(hotplaceComment.hotplace.eq(hotplace))
            .orderBy(hotplaceComment.likes.size().desc())
            .fetchFirst()
    }
}