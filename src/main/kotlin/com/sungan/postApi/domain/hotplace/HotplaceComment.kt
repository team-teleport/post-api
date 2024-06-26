package com.sungan.postApi.domain.hotplace

import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.BestCommentVo
import com.sungan.postApi.dto.HotplaceBestCommentVo
import com.sungan.postApi.dto.HotplaceCommentVo
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE hotplace_comment SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(
    indexes = [
        Index(name = "hotplace_id_idx", columnList = "hotplace_id")
    ]
)
class HotplaceComment(
    content: String,
    @Embedded
    var userInfo: UserInfo,
    @ManyToOne
    @JoinColumn(name = "hotplace_id")
    val hotplace: Hotplace
): PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var content: String = content

    @OneToMany(mappedBy = "hotplaceComment", cascade = [CascadeType.REMOVE])
    var likes: MutableList<HotplaceCommentLike> = ArrayList()

    @OneToMany(mappedBy = "hotplaceComment", cascade = [CascadeType.REMOVE])
    @OrderBy(value = "created_at DESC")
    var nestedComments: MutableList<HotplaceNestedComment> = ArrayList()

    fun convertToVo() = HotplaceCommentVo(
        id!!,
        content,
        userInfo,
        hotplace.id!!,
        nestedComments.map { nestedComment -> nestedComment.convertToVo() },
        createdAt,
        updatedAt
    )

    fun convertToBestComment() = HotplaceBestCommentVo(id!!, content, userInfo, createdAt, updatedAt, hotplace.id!!)
}