package com.sungan.postApi.repository.query

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.QHotplaceComment.hotplaceComment
import com.sungan.postApi.domain.hotplace.QHotplaceNestedComment.hotplaceNestedComment

class HotplaceNestedCommentQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : HotplaceNestedCommentQueryRepository {
    override fun deleteAllByHotplace(hotplace: Hotplace) {
        val comments = queryFactory.selectFrom(hotplaceComment).where(hotplaceComment.hotplace.eq(hotplace))
        queryFactory.update(hotplaceNestedComment)
            .set(hotplaceNestedComment.deleted, true)
            .where(hotplaceNestedComment.hotplaceComment.`in`(comments))
            .execute()
    }
}