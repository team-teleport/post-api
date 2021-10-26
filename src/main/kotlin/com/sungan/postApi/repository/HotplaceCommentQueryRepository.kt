package com.sungan.postApi.repository

import com.sungan.postApi.domain.Hotplace
import com.sungan.postApi.domain.HotplaceComment

interface HotplaceCommentQueryRepository {
    fun findByHotplaceOrderByLikes(hotplace: Hotplace): HotplaceComment?
}