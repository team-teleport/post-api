package com.sungan.postApi.domain

import com.sungan.postApi.dto.NestedCommentVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class NestedComment(
    @ManyToOne
    @JoinColumn(name = "comment_id")
    var comment: Comment,
    @Column(nullable = false)
    var userId: Long,
    @Column(nullable = false)
    var content: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun convertToVo() = NestedCommentVo(
        id,
        userId,
        content,
        comment.id!!,
        createdAt,
        updatedAt
    )
}