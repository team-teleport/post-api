package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.Hotplace

interface HotplaceCommentLikeQueryRepository {
    fun deleteAllByHotplace(hot: Hotplace)
}