package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import javax.persistence.*

@Entity
class ReportCommentLike(
    report: Report,
    userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val userId: Long = userId

    @ManyToOne
    @JoinColumn(name = "report_id")
    val report: Report = report

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(ReportCommentLike::id)
        private val toStringProperties = arrayOf(
            ReportCommentLike::id,
            ReportCommentLike::userId,
            ReportCommentLike::report
        )
    }
}