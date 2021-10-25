package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.domain.UserViewedSungan
import com.sungan.postApi.dto.*
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SunganService(
    val sunganRepository: SunganRepository,
    val sunganLikeRepository: SunganLikeRepository,
    val userViewedSunganRepository: UserViewedSunganRepository,
    val line2StationRepository: Line2StationRepository,
    val sunganChannelRepository: SunganChannelRepository
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
        val station = line2StationRepository.findByName(createSunganRequestDto.stationName) ?: throw SunganException(
            SunganError.BAD_REQUEST
        )
        val sunganChannel = sunganChannelRepository.findById(createSunganRequestDto.channelId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        val sungan = sunganRepository.save(
            Sungan(
                createSunganRequestDto.text,
                userId,
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
        if (userId != sungan.userId) throw SunganException(SunganError.FORBIDDEN)

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
        if (userId != sungan.userId) throw SunganException(SunganError.FORBIDDEN)
        val vo = sungan.convertToVo() // sungan을 리포지토리로 delete하면 여기서도 사용 불가, vo에 저장해놓기
        sunganRepository.delete(sungan)
        return vo
    }

    fun readMainSungansBeforeId(
        firstSunganId: Long,
        userId: Long,
        getMainRequestDto: GetMainRequestDto
    ): List<SunganWithLikeByUser> {

        val firstSungan = sunganRepository.findById(firstSunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }

        val sungans = sunganRepository.findSungansBeforeFirstSunganPaging(getMainRequestDto, firstSungan)
        return sungans.asSequence().map { sungan ->
            userViewedSunganRepository.save(UserViewedSungan(sungan, userId))
            SunganWithLikeByUser(
                sungan.convertToVo(),
                sunganLikeRepository.findByUserIdAndSungan(userId, sungan) != null
            )
        }.toList()
    }

    fun readMainSungansAfterId(
        lastSunganId: Long?,
        userId: Long,
        getMainRequestDto: GetMainRequestDto
    ): List<SunganWithLikeByUser> {
        if (lastSunganId == null) {
            return findTopTen(userId, getMainRequestDto)
        }
        val lastSungan = sunganRepository.findById(lastSunganId)
            .orElseThrow { throw SunganException(SunganError.BAD_REQUEST_INVALID_ID) }
        val sungans = sunganRepository.findSungansAfterLastSunganPaging(getMainRequestDto, lastSungan)
        return sungans.asSequence().map { sungan ->
            userViewedSunganRepository.save(UserViewedSungan(sungan, userId))
            SunganWithLikeByUser(
                sungan.convertToVo(),
                sunganLikeRepository.findByUserIdAndSungan(userId, sungan) != null
            )
        }.toList()
    }

    fun findTopTen(userId: Long, getMainRequestDto: GetMainRequestDto): List<SunganWithLikeByUser> {
        val sungans: MutableList<Sungan> = sunganRepository.findLimitSizeOrderByDesc(userId, getMainRequestDto)
        return sungans.asSequence().map { sungan ->
            userViewedSunganRepository.save(UserViewedSungan(sungan, userId))
            SunganWithLikeByUser(
                sungan.convertToVo(),
                sunganLikeRepository.findByUserIdAndSungan(userId, sungan) != null
            )
        }.toList()
    }
}