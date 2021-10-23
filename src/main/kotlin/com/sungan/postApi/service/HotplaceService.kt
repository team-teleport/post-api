package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Hotplace
import com.sungan.postApi.dto.HotplaceVo
import com.sungan.postApi.dto.PostHotplaceReqDto
import com.sungan.postApi.repository.HotplaceRepository
import com.sungan.postApi.repository.Line2StationRepository
import org.springframework.stereotype.Service

@Service
class HotplaceService(
    val hotplaceRepository: HotplaceRepository,
    val line2StationRepository: Line2StationRepository
) {
    fun createHotplace(userId: Long, postHotplaceReqDto: PostHotplaceReqDto): HotplaceVo {
        val hotplace = hotplaceRepository.save(Hotplace(
            postHotplaceReqDto.title,
            postHotplaceReqDto.text,
            userId,
            line2StationRepository.findByName(postHotplaceReqDto.stationName) ?: throw SunganException(SunganError.BAD_REQUEST),
            postHotplaceReqDto.place
        ))
        return hotplace.convertToVo()
    }

    fun readHotplace(userId: Long, hotplaceId: Long): HotplaceVo {
        val hotplace = hotplaceRepository.findById(hotplaceId).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        return hotplace.convertToVo()
    }
}