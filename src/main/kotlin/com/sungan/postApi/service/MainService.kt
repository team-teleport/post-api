package com.sungan.postApi.service

import com.sungan.postApi.dto.PostBaseVo
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
    fun getMySunganList(userId: Long): MutableList<PostBaseVo> {
        val list: MutableList<PostBaseVo> = ArrayList()
        list.addAll(sunganRepository.findByUserId(userId).asSequence().map { user -> user.convertToVo() })
        list.addAll(reportRepository.findByUserId(userId).asSequence().map { report -> report.convertToVo() })
        list.addAll(hotplaceRepository.findByUserId(userId).asSequence().map { hotplace -> hotplace.convertToVo() })
        list.sortWith { a, b -> if (a.createdAt.isBefore(b.createdAt)) 1 else -1 }
        return list
    }
}