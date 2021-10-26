package com.sungan.postApi.domain

import com.sungan.postApi.dto.HotplaceCommentVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class HotplaceComment(
    content: String,
    userId: Long,
    @ManyToOne
    @JoinColumn(name = "hotplace_id")
    val hotplace: Hotplace
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var content: String = content

    @Column(nullable = false)
    val userId: Long = userId

    @OneToMany(mappedBy = "hotplaceComment")
    var likes: MutableList<HotplaceCommentLike> = ArrayList()

    @OneToMany(mappedBy = "hotplaceComment")
    @OrderBy(value = "created_at DESC")
    var nestedComments: MutableList<HotplaceNestedComment> = ArrayList()

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun convertToVo() = HotplaceCommentVo(
        id!!,
        content,
        userId,
        hotplace.id!!,
        nestedComments.map { nestedComment -> nestedComment.convertToVo() },
        createdAt,
        updatedAt
    )
}