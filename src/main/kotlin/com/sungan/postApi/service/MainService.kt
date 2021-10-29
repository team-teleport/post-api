package com.sungan.postApi.service

import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.dto.PostBaseVo
import com.sungan.postApi.dto.PostBaseWithLikeByUserAndBestComment
import com.sungan.postApi.dto.PostType
import com.sungan.postApi.repository.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
    val hotplaceCommentRepository: HotplaceCommentRepository,
    val line2StationRepository: Line2StationRepository
) {
    fun getMySunganList(userId: Long): MutableList<PostBaseWithLikeByUserAndBestComment> {
        val list: MutableList<PostBaseWithLikeByUserAndBestComment> = ArrayList()
        list.addAll(getMySunganWithLikeByUserAndBestComment(userId))
        list.addAll(getMyReportWithLikeByUserAndBestComment(userId))
        list.addAll(getMyHotplaceWithLikeByUserAndBestComment(userId))
        list.sortWith { a, b -> if (a.post.createdAt.isBefore(b.post.createdAt)) 1 else -1 }
        return list
    }

    fun getMainList(userId: Long, station: String?): MutableList<PostBaseWithLikeByUserAndBestComment> {
        val list: MutableList<PostBaseWithLikeByUserAndBestComment> = ArrayList()
        val now = LocalDateTime.now()

        if (station.isNullOrBlank()) {
            list.addAll(getSunganWithLikeByUserAndBestComment(userId, now))
            list.addAll(getHotplaceWithLikeByUserAndBestComment(userId, now))
        } else {
            list.addAll(getSunganSpecificStationWithLikeByUserAndBestComment(userId, now, station))
            list.addAll(getHotplaceSpecificStationWithLikeByUserAndBestComment(userId, now, station))
        }
        list.addAll(getReportWithLikeByUserAndBestComment(userId, now))
        list.sortWith { a, b -> if (a.post.createdAt.isBefore(b.post.createdAt)) 1 else -1 }
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

    fun getReportWithLikeByUserAndBestComment(
        userId: Long,
        now: LocalDateTime
    ): List<PostBaseWithLikeByUserAndBestComment> {
        val reports = reportRepository.findByCreatedAtBetween(now.minusDays(1), now)
        return reports.map { report ->
            PostBaseWithLikeByUserAndBestComment(
                report.convertToVo(),
                PostType.REPORT,
                reportLikeRepository.findByReportAndUserId(report, userId) != null,
                reportCommentRepository.findByReportOrderByLikes(report)?.convertToVo()
            )
        }
    }

    fun getSunganSpecificStationWithLikeByUserAndBestComment(
        userId: Long,
        now: LocalDateTime,
        stationName: String
    ): List<PostBaseWithLikeByUserAndBestComment> {
        val station = line2StationRepository.findByName(stationName) ?: throw SunganException(SunganError.BAD_REQUEST)
        val sungans = sunganRepository.findByStationAndCreatedAtBetween(station, now.minusDays(1), now)
        return sungans.map { sungan ->
            PostBaseWithLikeByUserAndBestComment(
                sungan.convertToVo(),
                PostType.SUNGAN,
                sunganLikeRepository.findByUserIdAndSungan(userId, sungan) != null,
                commentRepository.findBySunganOrderByLikes(sungan)?.convertToVo()
            )
        }
    }
    fun getMySunganWithLikeByUserAndBestComment(
        userId: Long
    ): List<PostBaseWithLikeByUserAndBestComment> {
        val sungans = sunganRepository.findByUserInfoUserId(userId)
        return sungans.map { sungan ->
            PostBaseWithLikeByUserAndBestComment(
                sungan.convertToVo(),
                PostType.SUNGAN,
                sunganLikeRepository.findByUserIdAndSungan(userId, sungan) != null,
                commentRepository.findBySunganOrderByLikes(sungan)?.convertToVo()
            )
        }
    }

    fun getHotplaceWithLikeByUserAndBestComment(
        userId: Long,
        now: LocalDateTime
    ): List<PostBaseWithLikeByUserAndBestComment> {
        val hotplaces = hotplaceRepository.findByCreatedAtBetween(now.minusDays(1), now)
        return hotplaces.map { hotplace ->
            PostBaseWithLikeByUserAndBestComment(
                hotplace.convertToVo(),
                PostType.PLACE,
                hotplaceLikeRepository.findByHotplaceAndUserId(hotplace, userId) != null,
                hotplaceCommentRepository.findByHotplaceOrderByLikes(hotplace)?.convertToVo()
            )
        }
    }

    fun getMyHotplaceWithLikeByUserAndBestComment(
        userId: Long
    ): List<PostBaseWithLikeByUserAndBestComment> {
        return hotplaceRepository.findByUserInfoUserId(userId).map { hotplace ->
            PostBaseWithLikeByUserAndBestComment(
                hotplace.convertToVo(),
                PostType.PLACE,
                hotplaceLikeRepository.findByHotplaceAndUserId(hotplace, userId) != null,
                hotplaceCommentRepository.findByHotplaceOrderByLikes(hotplace)?.convertToVo()
            )
        }
    }

    fun getHotplaceSpecificStationWithLikeByUserAndBestComment(
        userId: Long,
        now: LocalDateTime,
        stationName: String
    ): List<PostBaseWithLikeByUserAndBestComment> {
        val station = line2StationRepository.findByName(stationName) ?: throw SunganException(SunganError.BAD_REQUEST)
        val places = hotplaceRepository.findByStationAndCreatedAtBetween(station, now.minusDays(1), now)
        return places.map { place ->
            PostBaseWithLikeByUserAndBestComment(
                place.convertToVo(),
                PostType.PLACE,
                hotplaceLikeRepository.findByHotplaceAndUserId(place, userId) != null,
                hotplaceCommentRepository.findByHotplaceOrderByLikes(place)?.convertToVo()
            )
        }
    }

    fun getMyReportWithLikeByUserAndBestComment(
        userId: Long
    ): List<PostBaseWithLikeByUserAndBestComment> {
        return reportRepository.findByUserInfoUserId(userId).map { report ->
            PostBaseWithLikeByUserAndBestComment(
                report.convertToVo(),
                PostType.REPORT,
                reportLikeRepository.findByReportAndUserId(report, userId) != null,
                reportCommentRepository.findByReportOrderByLikes(report)?.convertToVo()
            )
        }
    }

}