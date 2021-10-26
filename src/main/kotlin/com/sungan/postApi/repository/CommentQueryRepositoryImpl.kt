package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.Comment
import com.sungan.postApi.domain.QComment.comment
import com.sungan.postApi.domain.Sungan

class CommentQueryRepositoryImpl(
    val query: JPAQueryFactory
): CommentQueryRepository {
    override fun findBySunganOrderByLikes(sungan: Sungan): Comment? {
        return query.selectFrom(comment)
            .where(comment.sungan.eq(sungan))
            .orderBy(comment.likes.size().desc())
            .fetchOne()
    }
}