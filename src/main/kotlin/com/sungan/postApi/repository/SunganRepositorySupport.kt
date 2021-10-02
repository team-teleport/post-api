package com.sungan.postApi.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.dto.SunganVo
import org.springframework.data.domain.PageImpl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class SunganRepositorySupport(
    val queryFactory: JPAQueryFactory,
    ): QuerydslRepositorySupport(Sungan::class.java) {
}