package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceComment
import org.springframework.data.jpa.repository.JpaRepository

interface HotplaceCommentRepository: JpaRepository<HotplaceComment, Long>, HotplaceCommentQueryRepository {
    fun findByHotplaceOrderByCreatedAtDesc(hotplace: Hotplace): MutableList<HotplaceComment>
    fun countByHotplace(hotplace: Hotplace): Long
}