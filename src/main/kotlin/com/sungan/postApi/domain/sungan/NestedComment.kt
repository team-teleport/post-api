package com.sungan.postApi.domain.sungan

import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.NestedCommentVo
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE nested_comment SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(
    indexes = [
        Index(name = "nexted_comment_idx", columnList = "comment_id, deleted")
    ]
)
class NestedComment(
    @ManyToOne
    @JoinColumn(name = "comment_id")
    var comment: Comment,
    @Embedded
    var userInfo: UserInfo,
    @Column(nullable = false)
    var content: String
): PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    fun convertToVo() = NestedCommentVo(
        id,
        userInfo,
        content,
        comment.id!!,
        createdAt,
        updatedAt
    )
}