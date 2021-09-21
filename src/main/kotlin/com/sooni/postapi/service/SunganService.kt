package com.sooni.postapi.service

import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.domain.User
import com.sooni.postapi.dto.SunganDto
import com.sooni.postapi.repository.CommentLikeRepository
import com.sooni.postapi.repository.CommentRepository
import com.sooni.postapi.repository.SunganContentsRepository
import com.sooni.postapi.repository.SunganRepository
import org.springframework.stereotype.Service

@Service
class SunganService(
    val sunganRepository: SunganRepository,
    val sunganContentsRepository: SunganContentsRepository,
    val commentRepository: CommentRepository,
    val commentLikeRepository: CommentLikeRepository,
) {
    fun readSunganById(user: User, id: Long): SunganDto {
        val sungan = sunganRepository.findById(id).orElseThrow { throw SunganException(SunganError.BAD_REQUEST) }
        sungan.readCnt += 1
        return SunganDto(
            user == sungan.user,
            sungan.convertToVo()
        )
    }
}