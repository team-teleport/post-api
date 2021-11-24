package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.sungan.Sungan
import com.sungan.postApi.dto.*
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SunganService(
    val sunganRepository: SunganRepository,
    val sunganLikeRepository: SunganLikeRepository,
    val commentRepository: CommentRepository,
    val line2StationRepository: Line2StationRepository,
    val sunganChannelRepository: SunganChannelRepository
) {
    fun readSunganById(readSunganDto: ReadSunganDto): SunganDto {
        val (userId, id) = readSunganDto
        val sungan =
            sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        sungan.readCnt += 1

        return SunganDto(
            userId == sungan.userInfo.userId,
            sunganRepository.save(sungan).convertToVo()
        )
    }

    fun readSungans(
        userId: Long,
        getSunganByChannelReqDto: GetSunganByChannelReqDto,
        stationName: String? = null
    ): List<PostBaseWithLikeByUserAndBestComment> {
        val channel = sunganChannelRepository.findById(getSunganByChannelReqDto.channelId)
            .orElseThrow { SunganException(SunganError.ENTITY_NOT_FOUND, "순간 채널 id로 채널 찾을 수 없음") }
        var station: Line2Station? = null
        if (stationName != null) {
            station = line2StationRepository.findByName(stationName) ?: throw SunganException(
                SunganError.ENTITY_NOT_FOUND,
                "해당 이름의 역을 찾을 수 없습니다."
            )
        }
        return sunganRepository.findSungansAfterLastSunganPagingOrderByCreatedAtDesc(
            getSunganByChannelReqDto.size,
            getSunganByChannelReqDto.lastId,
            channel,
            station,
        ).map { sungan ->
            PostBaseWithLikeByUserAndBestComment(
                sungan.convertToVo(),
                PostType.SUNGAN,
                sunganLikeRepository.existsBySunganAndUserId(sungan, userId),
                commentRepository.findBySunganOrderByLikes(sungan)?.convertToVo()
            )
        }
    }

    fun createSungan(userId: Long, createSunganRequestDto: CreateSunganRequestDto): SunganDto {
        val station = if (createSunganRequestDto.stationName != null) {
            line2StationRepository.findByName(createSunganRequestDto.stationName) ?: throw SunganException(
                SunganError.BAD_REQUEST
            )
        } else {
            null
        }
        val sunganChannel = sunganChannelRepository.findById(createSunganRequestDto.channelId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val sungan = sunganRepository.save(
            Sungan(
                createSunganRequestDto.text,
                createSunganRequestDto.makeUserInfo(userId),
                station,
                createSunganRequestDto.emoji,
                sunganChannel
            )
        )
        return SunganDto(
            true,
            sungan.convertToVo()
        )
    }

    fun updateSungan(userId: Long, patchSunganRequestDto: PatchSunganRequestDto): SunganDto {
        val sungan = sunganRepository.findById(patchSunganRequestDto.sunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != sungan.userInfo.userId) throw SunganException(SunganError.FORBIDDEN)

        sungan.text = patchSunganRequestDto.text ?: sungan.text
        sungan.emoji = patchSunganRequestDto.emoji ?: sungan.emoji
        return SunganDto(
            true,
            sunganRepository.save(sungan).convertToVo()
        )
    }

    fun destroySungan(userId: Long, id: Long): SunganVo {
        val sungan =
            sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        if (userId != sungan.userInfo.userId) throw SunganException(SunganError.FORBIDDEN)
        val vo = sungan.convertToVo() // sungan을 리포지토리로 delete하면 여기서도 사용 불가, vo에 저장해놓기
        sunganRepository.delete(sungan)
        return vo
    }
}