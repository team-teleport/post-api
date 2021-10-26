package com.sungan.postApi.repository

import com.sungan.postApi.domain.Hotplace
import com.sungan.postApi.domain.HotplaceComment
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceCommentRepository: JpaRepository<HotplaceComment, Long>, HotplaceCommentQueryRepository {
    fun findByHotplaceOrderByCreatedAtDesc(hotplace: Hotplace): MutableList<HotplaceComment>
    fun countByHotplace(hotplace: Hotplace): Long
}