package com.sungan.postApi.repository

import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.dto.GetMainRequestDto

interface SunganQueryRepository {
    fun findSungansAfterLastSunganPaging(getMainRequestDto: GetMainRequestDto, lastSungan: Sungan): MutableList<Sungan>
    fun findSungansBeforeFirstSunganPaging(getMainRequestDto: GetMainRequestDto, firstSungan: Sungan): MutableList<Sungan>
    fun findLimitSizeOrderByDesc(userId: Long, getMainRequestDto: GetMainRequestDto): MutableList<Sungan>
}