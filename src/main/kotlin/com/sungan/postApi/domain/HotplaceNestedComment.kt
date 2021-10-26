package com.sungan.postApi.domain

import com.sungan.postApi.dto.HotplaceNestedCommentVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class HotplaceNestedComment(
    @ManyToOne
    @JoinColumn(name = "hotplace_comment_id")
    var hotplaceComment: HotplaceComment,
    @Column(nullable = false)
    var content: String,
    val userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun convertToVo() = HotplaceNestedCommentVo(id!!, hotplaceComment.id!!, content, userId, createdAt, updatedAt)
}