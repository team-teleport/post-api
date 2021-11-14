package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceComment

interface HotplaceCommentQueryRepository {
    fun findByHotplaceOrderByLikes(hotplace: Hotplace): HotplaceComment?
}