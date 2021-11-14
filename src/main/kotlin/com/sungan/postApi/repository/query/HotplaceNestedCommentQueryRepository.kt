package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.hotplace.Hotplace

interface HotplaceNestedCommentQueryRepository {
    fun deleteAllByHotplace(hotplace: Hotplace)
}