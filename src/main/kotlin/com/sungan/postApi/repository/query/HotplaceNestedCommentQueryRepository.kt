package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceComment

interface HotplaceNestedCommentQueryRepository {
    fun deleteAllByHotplace(hotplace: Hotplace)
    fun deleteAllByHotplaceComment(hotplaceComment: HotplaceComment)
}