package com.sungan.postApi.repository

import com.sungan.postApi.domain.ReportNestedComment
import org.springframework.data.jpa.repository.JpaRepository

interface ReportNestedCommentRepository:JpaRepository<ReportNestedComment, Long> {
}