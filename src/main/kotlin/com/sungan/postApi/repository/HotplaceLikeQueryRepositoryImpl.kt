package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.QHotplaceLike.hotplaceLike

class HotplaceLikeQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): HotplaceLikeQueryRepository {
    override fun deleteAllByHotplace(hot: Hotplace) {
        queryFactory.delete(hotplaceLike)
            .where(hotplaceLike.hotplace.eq(hot))
            .execute()
    }
}