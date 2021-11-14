package com.sungan.postApi.domain.report

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import javax.persistence.*

@Entity
class ReportCommentLike(
    reportComment: ReportComment,
    userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val userId: Long = userId

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "report_comment_id")
    val reportComment: ReportComment = reportComment

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(ReportCommentLike::id)
        private val toStringProperties = arrayOf(
            ReportCommentLike::id,
            ReportCommentLike::userId,
            ReportCommentLike::reportComment
        )
    }
}