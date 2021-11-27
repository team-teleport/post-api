package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.sungan.Sungan
import com.sungan.postApi.domain.sungan.SunganChannel
import com.sungan.postApi.dto.GetMainRequestDto
import java.time.LocalDateTime

interface SunganQueryRepository {
    fun findSungansAfterLastSunganPagingOrderByCreatedAtDesc(size: Long, lastSunganId: Long?, channel: SunganChannel? = null, station: Line2Station? = null): MutableList<Sungan>
    fun findSungansBeforeLastCreatedAtPaging(size: Long, lastCreatedAt: LocalDateTime?, station: Line2Station?): MutableList<Sungan>
}