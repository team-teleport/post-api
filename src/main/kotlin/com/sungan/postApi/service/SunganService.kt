package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.DetailHashTag
import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.domain.UserViewdSungan
import com.sungan.postApi.dto.*
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service

@Service
class SunganService(
    val sunganRepository: SunganRepository,
    val mainHashTagRepository: MainHashTagRepository,
    val vehicleRepository: VehicleRepository,
    val detailHashTagRepository: DetailHashTagRepository,
    val userViewdSunganRepository: UserViewdSunganRepository
) {
    fun readSunganById(readSunganDto: ReadSunganDto): SunganDto {
        val (userId, id) = readSunganDto
        val sungan =
            sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        sungan.readCnt += 1

        return SunganDto(
            userId == sungan.userId,
            sunganRepository.save(sungan).convertToVo()
        )
    }

    fun createSungan(userId: Long, createSunganRequestDto: CreateSunganRequestDto): SunganDto {
        val sungan = sunganRepository.save(
            Sungan(
                createSunganRequestDto.title,
                createSunganRequestDto.text,
                userId,
                vehicleRepository.findById(createSunganRequestDto.vehicleName).orElseThrow {
                    SunganException(SunganError.BAD_REQUEST_INVALID_ID)
                },
                createSunganRequestDto.emoji
            )
        )
        sungan.mainHashTag = createSunganRequestDto.mainHashTagId?.let {
            mainHashTagRepository.findById(it)
                .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        }
        createSunganRequestDto.detailHashTag?.forEach {
            sungan.detailHashTags += detailHashTagRepository.findByName(it) ?: detailHashTagRepository.save(
                DetailHashTag(it)
            )
        }
        sunganRepository.save(sungan) // 더티체킹 일어나지 않음
        return SunganDto(
            true,
            sungan.convertToVo()
        )
    }

    fun updateSungan(userId: Long, patchSunganRequestDto: PatchSunganRequestDto): SunganDto {
        val sungan = sunganRepository.findById(patchSunganRequestDto.sunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != sungan.userId) throw SunganException(SunganError.FORBIDDEN)

        sungan.title = patchSunganRequestDto.title ?: sungan.title
        sungan.text = patchSunganRequestDto.text ?: sungan.text
        sungan.mainHashTag = patchSunganRequestDto.mainHashTagId?.let {
            mainHashTagRepository.findById(patchSunganRequestDto.mainHashTagId)
                .orElseThrow { SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        }
        sungan.emoji = patchSunganRequestDto.emoji ?: sungan.emoji
        patchSunganRequestDto.detailHashTag?.forEach {
            sungan.detailHashTags += detailHashTagRepository.findByName(it) ?: detailHashTagRepository.save(
                DetailHashTag(it)
            )
        }
        return SunganDto(
            true,
            sunganRepository.save(sungan).convertToVo()
        )
    }

    fun destroySungan(userId: Long, id: Long): SunganVo {
        val sungan =
            sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != sungan.userId) throw SunganException(SunganError.FORBIDDEN)
        val vo = sungan.convertToVo() // sungan을 리포지토리로 delete하면 여기서도 사용 불가, vo에 저장해놓기
        sunganRepository.delete(sungan)
        return vo
    }

    fun readMainSungans(userId: Long, getMainRequestDto: GetMainRequestDto): List<SunganVo> {
        val sungans: MutableList<Sungan> =
            sunganRepository.findMainByUserIdAndVehicleAndPaging(userId, getMainRequestDto)
        return sungans.asSequence().map { sungan ->
            userViewdSunganRepository.save(
                UserViewdSungan(
                    sungan,
                    userId
                )
            )
            sungan.convertToVo()
        }.toList()
    }


}