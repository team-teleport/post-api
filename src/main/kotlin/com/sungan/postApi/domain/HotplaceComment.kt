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
    val id: Long? = null

    @Column(nullable = false)
    var content: String = content

    @Column(nullable = false)
    val userId: Long = userId

    @OneToMany(mappedBy = "")
    var likes: MutableList<HotplaceCommentLike> = ArrayList()

    @OneToMany(mappedBy = "hotplaceComment")
    var nestedComments: MutableList<HotplaceNestedComment> = ArrayList()

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    fun convertToVo() = HotplaceCommentVo(id!!, content, userId, hotplace.id!!, nestedComments.map())
}