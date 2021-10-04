package com.sungan.postApi.repository

import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.dto.GetMainRequestDto

interface SunganQueryRepository {
    fun findMainByUserIdAndVehicleAndPaging(userId: Long, getMainRequestDto: GetMainRequestDto): MutableList<Sungan>
}