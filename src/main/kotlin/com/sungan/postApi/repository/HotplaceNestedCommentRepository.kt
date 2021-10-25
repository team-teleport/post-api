package com.sungan.postApi.repository

import com.sungan.postApi.domain.HotplaceNestedComment
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceNestedCommentRepository: JpaRepository<HotplaceNestedComment, Long> {
}