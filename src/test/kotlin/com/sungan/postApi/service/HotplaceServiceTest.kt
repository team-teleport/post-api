package com.sungan.postApi.service

import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.hotplace.HotplaceComment
import com.sungan.postApi.domain.hotplace.HotplaceCommentLike
import com.sungan.postApi.domain.hotplace.HotplaceLike
import com.sungan.postApi.repository.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
internal class HotplaceServiceTest @Autowired constructor(
    private val hotplaceService: HotplaceService,
    private val hotplaceRepository: HotplaceRepository,
    private val line2StationRepository: Line2StationRepository,
    private val hotplaceCommentRepository: HotplaceCommentRepository,
    private val hotplaceLikeRepository: HotplaceLikeRepository,
    private val hotplaceCommentLikeRepository: HotplaceCommentLikeRepository,
) {
    @Test
    fun `Hotplace 삭제 시 댓글과 좋아요도 삭제된다`() {
        val station = line2StationRepository.save(Line2Station("성수"))
        val hotplace = hotplaceRepository.save(
            Hotplace(
                text = "핫플이에요.",
                userInfo = UserInfo(userId = 1, userName = "가용", userProfileImgUrl = null),
                station,
                place = "질서정연",
                emoji = null
            )
        )

        val comment = hotplaceCommentRepository.save(
            HotplaceComment(
                content = "댓글이에요",
                userInfo = UserInfo(userId = 2, userName = "융가", userProfileImgUrl = null),
                hotplace
            )
        )
        val commentLike = hotplaceCommentLikeRepository.save(
            HotplaceCommentLike(
                userId = 3,
                comment
            )
        )
        val hotplaceId = hotplace.id ?: fail("핫플레이스 생성 실패")

        val like = hotplaceLikeRepository.save(
            HotplaceLike(
                userId = 2,
                hotplace
            )
        )

        hotplaceService.destroyHotplace(1, hotplaceId)
        assertThat(hotplaceCommentLikeRepository.findById(commentLike.id!!)).isEmpty
        assertThat(hotplaceCommentRepository.findById(comment.id!!)).isEmpty
        assertThat(hotplaceLikeRepository.findById(like.id!!)).isEmpty
    }
}