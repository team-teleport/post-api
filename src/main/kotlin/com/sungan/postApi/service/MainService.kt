package com.sungan.postApi.service

import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.repository.HotplaceRepository
import com.sungan.postApi.repository.ReportRepository
import com.sungan.postApi.repository.SunganRepository
import org.springframework.stereotype.Service

@Service
class MainService(
    val sunganRepository: SunganRepository,
    val reportRepository: ReportRepository,
    val hotplaceRepository: HotplaceRepository,
) {
    fun getMySunganList(userId: Long): MutableList<PostBaseEntity> {
        val list: MutableList<PostBaseEntity> = ArrayList()
        list.addAll(sunganRepository.findByUserId(userId))
        list.addAll(reportRepository.findByUserId(userId))
        list.addAll(hotplaceRepository.findByUserId(userId))
        list.sortWith { a, b -> if (a.createdAt.isBefore(b.createdAt)) -1 else 1 }
        return list
    }
}