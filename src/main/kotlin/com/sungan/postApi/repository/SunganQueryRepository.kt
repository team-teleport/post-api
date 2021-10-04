package com.sungan.postApi.repository

import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.dto.GetMainRequestDto

interface SunganQueryRepository {
    fun findMainByUserIdAndVehicleOrderByCreateAt(userId: Long, getMainRequestDto: GetMainRequestDto): MutableList<Sungan>
    fun findMainByUserIdAndVehicleOrderByLikeCnt(userId: Long, getMainRequestDto: GetMainRequestDto): MutableList<Sungan>
    fun findMainByUserIdAndVehicleOrderByReadCnt(userId: Long, getMainRequestDto: GetMainRequestDto): MutableList<Sungan>
}