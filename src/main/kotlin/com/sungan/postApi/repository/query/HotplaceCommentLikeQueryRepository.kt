package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.hotplace.Hotplace

interface HotplaceCommentLikeQueryRepository {
    fun deleteAllByHotplace(hot: Hotplace)
}