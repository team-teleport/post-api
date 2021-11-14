package com.sungan.postApi.repository

import com.sungan.postApi.domain.hotplace.Hotplace

interface HotplaceLikeQueryRepository {
    fun deleteAllByHotplace(hot: Hotplace)
}