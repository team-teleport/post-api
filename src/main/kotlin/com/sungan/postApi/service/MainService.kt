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
    val sunganLikeRepository: SunganLikeRepository,
    val reportLikeRepository: ReportLikeRepository,
    val hotplaceLikeRepository: HotplaceLikeRepository,
    val commentRepository: CommentRepository,
    val reportCommentRepository: ReportCommentRepository,
    val hotplaceCommentRepository: HotplaceCommentRepository
) {
    fun getMySunganList(userId: Long): MutableList<PostBaseVo> {
        val list: MutableList<PostBaseVo> = ArrayList()
        list.addAll(sunganRepository.findByUserId(userId).asSequence().map { user -> user.convertToVo() })
        list.addAll(reportRepository.findByUserId(userId).asSequence().map { report -> report.convertToVo() })
        list.addAll(hotplaceRepository.findByUserId(userId).asSequence().map { hotplace -> hotplace.convertToVo() })
        list.sortWith { a, b -> if (a.createdAt.isBefore(b.createdAt)) 1 else -1 }
        return list
    }
    fun getSunganWithLikeByUserAndBestComment(
        userId: Long,
        now: LocalDateTime
    ): List<PostBaseWithLikeByUserAndBestComment> {
        val sungans = sunganRepository.findByCreatedAtBetween(now.minusDays(1), now)
        return sungans.map { sungan ->
            PostBaseWithLikeByUserAndBestComment(
                sungan.convertToVo(),
                PostType.SUNGAN,
                sunganLikeRepository.findByUserIdAndSungan(userId, sungan) != null,
                commentRepository.findBySunganOrderByLikes(sungan)?.convertToVo()
            )
        }
    }