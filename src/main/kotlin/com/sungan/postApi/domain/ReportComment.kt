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

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime

    fun convertToVo() = ReportCommentVo(
        id!!,
        userId,
        reportId = report.id!!,
        content,
        createdAt,
        updatedAt
    )
}