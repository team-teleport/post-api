package com.sungan.postApi.repository

import com.sungan.postApi.domain.NestedComment
import org.springframework.data.jpa.repository.JpaRepository

interface NestedCommentRepository: JpaRepository<NestedComment, Long> {
}