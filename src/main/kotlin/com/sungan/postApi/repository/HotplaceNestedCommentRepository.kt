package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.HotplaceNestedComment
import com.sungan.postApi.repository.query.HotplaceNestedCommentQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceNestedCommentRepository : JpaRepository<HotplaceNestedComment, Long>,
    HotplaceNestedCommentQueryRepository {
}