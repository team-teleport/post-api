package com.sungan.postApi.domain.report

import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.ReportNestedCommentVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ReportNestedComment(
    @ManyToOne
    @JoinColumn(name = "report_comment_id")
    var reportComment: ReportComment,
    @Column(nullable = false)
    var content: String,
    @Embedded
    var userInfo: UserInfo,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun convertToVo() = ReportNestedCommentVo(
        reportComment.id!!,
        userInfo,
        content,
        createdAt,
        updatedAt
    )
}