package com.sungan.postApi.domain.report

import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.ReportNestedCommentVo
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE report_nested_comment SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
class ReportNestedComment(
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "report_comment_id")
    var reportComment: ReportComment,
    @Column(nullable = false)
    var content: String,
    @Embedded
    var userInfo: UserInfo,
): PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    fun convertToVo() = ReportNestedCommentVo(
        reportComment.id!!,
        userInfo,
        content,
        createdAt,
        updatedAt
    )
}