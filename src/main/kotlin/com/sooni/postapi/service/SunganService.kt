package com.sooni.postapi.service

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.domain.DetailHashTag
import com.sooni.postapi.domain.Sungan
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.CreateSunganRequestDto
import com.sooni.postapi.dto.ReadSunganDto
import com.sooni.postapi.dto.SunganDto
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
        val sungan = sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
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
                    SunganException(SunganError.BAD_REQUEST)
                },
                createSunganRequestDto.emoji
            )
        )
        sungan.mainHashTag = createSunganRequestDto.mainHashTagId?.let {
            mainHashTagRepository.findById(it)
                .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
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
}