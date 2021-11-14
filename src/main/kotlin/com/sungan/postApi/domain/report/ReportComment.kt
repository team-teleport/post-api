package com.sungan.postApi.domain.report

import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.ReportCommentVo
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE table_product SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
class ReportComment(
    content: String,
    report: Report,
    @Embedded
    var userInfo: UserInfo,
): PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var content: String = content

    @ManyToOne
    @JoinColumn(name = "report_id")
    val report: Report = report

    @OneToMany(mappedBy = "reportComment")
    var likes: MutableList<ReportCommentLike> = ArrayList()

    @OneToMany(mappedBy = "reportComment")
    @OrderBy(value = "created_at DESC")
    var nestedComments: MutableList<ReportNestedComment> = ArrayList()

    fun convertToVo() = ReportCommentVo(
        id!!,
        userInfo,
        reportId = report.id!!,
        content,
        createdAt,
        updatedAt
    )
}