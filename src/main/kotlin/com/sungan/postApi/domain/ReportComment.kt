package com.sungan.postApi.domain

import com.sungan.postApi.dto.ReportCommentVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ReportComment(
    content: String,
    report: Report,
    @Column(nullable = false)
    val userId: Long
){
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

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun convertToVo() = ReportCommentVo(
        id!!,
        userId,
        reportId = report.id!!,
        content,
        createdAt,
        updatedAt
    )
}