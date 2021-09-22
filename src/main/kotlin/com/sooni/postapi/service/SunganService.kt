package com.sooni.postapi.service

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.domain.DetailHashTag
import com.sooni.postapi.domain.Sungan
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.*
import com.sooni.postapi.repository.*
import org.springframework.stereotype.Service

@Service
class SunganService(
    val sunganRepository: SunganRepository,
    val sunganContentsRepository: SunganContentsRepository,
    val commentRepository: CommentRepository,
    val commentLikeRepository: CommentLikeRepository,
    val mainHashTagRepository: MainHashTagRepository,
    val vehicleRepository: VehicleRepository,
    val detailHashTagRepository: DetailHashTagRepository
) {
    fun readSunganById(readSunganDto: ReadSunganDto): SunganDto {
        val (user, id) = readSunganDto
        val sungan =
            sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        sungan.readCnt += 1
        return SunganDto(
            user != null && user == sungan.user,
            sungan.convertToVo()
        )
    }

    fun createSungan(user: User, createSunganRequestDto: CreateSunganRequestDto): SunganDto {
        val sungan = sunganRepository.save(
            Sungan(
                createSunganRequestDto.title,
                createSunganRequestDto.text,
                user,
                vehicleRepository.findById(createSunganRequestDto.vehicleId).orElseThrow {
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
        return SunganDto(
            true,
            sungan.convertToVo()
        )
    }

    fun updateSungan(user: User, patchSunganRequestDto: PatchSunganRequestDto): SunganDto {
        val sungan = sunganRepository.findById(patchSunganRequestDto.sunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (user != sungan.user) throw SunganException(SunganError.FORBIDDEN)

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
            sungan.convertToVo()
        )
    }

    fun destroySungan(user: User, id: Long): SunganVo {
        val sungan =
            sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (user != sungan.user) throw SunganException(SunganError.FORBIDDEN)
        val vo = sungan.convertToVo() // sungan을 리포지토리로 delete하면 여기서도 사용 불가, vo에 저장해놓기
        sunganRepository.delete(sungan)
        return vo
    }
}