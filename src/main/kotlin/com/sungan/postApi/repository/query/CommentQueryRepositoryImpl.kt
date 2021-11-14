package com.sungan.postApi.repository.query

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.sungan.Comment
import com.sungan.postApi.domain.sungan.QComment.comment
import com.sungan.postApi.domain.sungan.Sungan

class CommentQueryRepositoryImpl(
    val query: JPAQueryFactory
) : CommentQueryRepository {
    override fun findBySunganOrderByLikes(sungan: Sungan): Comment? {
        return query.selectFrom(comment)
            .where(comment.sungan.eq(sungan))
            .orderBy(comment.likes.size().desc())
            .limit(1)
            .fetchOne()
    }
}