package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.hotplace.Hotplace
import java.time.LocalDateTime

interface HotplaceQueryRepository {
    fun findHotplacesAfterLastHotplacePagingOrderByCreatedAtDesc(
        size: Long,
        lastHotplaceId: Long? = null,
        station: Line2Station? = null
    ): MutableList<Hotplace>

    fun findHotplacesBeforeCreatedAtPaging(
        size: Long,
        lastCreatedAt: LocalDateTime?,
        station: Line2Station?
    ): MutableList<Hotplace>
}