package com.sungan.postApi.domain

import com.sungan.postApi.dto.ReportNestedCommentVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ReportNestedComment(
    @ManyToOne
    @JoinColumn(name = "reposrt_comment_id")
    var reportComment: ReportComment,
    @Column(nullable = false)
    var content: String,
    @Column(nullable = false)
    var userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    fun convertToVo() = ReportNestedCommentVo(
        reportComment.id!!,
        userId,
        content,
        createdAt,
        updatedAt
    )
}