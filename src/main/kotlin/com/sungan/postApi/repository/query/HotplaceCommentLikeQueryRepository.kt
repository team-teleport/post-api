package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceComment

interface HotplaceCommentLikeQueryRepository {
    fun deleteAllByHotplace(hot: Hotplace)
    fun deleteAllByHotplaceComment(hotplaceComment: HotplaceComment)
}