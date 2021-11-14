package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.sungan.Sungan
import com.sungan.postApi.dto.GetMainRequestDto

interface SunganQueryRepository {
    fun findSungansAfterLastSunganPaging(getMainRequestDto: GetMainRequestDto, lastSungan: Sungan): MutableList<Sungan>
    fun findSungansBeforeFirstSunganPaging(getMainRequestDto: GetMainRequestDto, firstSungan: Sungan): MutableList<Sungan>
    fun findLimitSizeOrderByDesc(userId: Long, getMainRequestDto: GetMainRequestDto): MutableList<Sungan>
}