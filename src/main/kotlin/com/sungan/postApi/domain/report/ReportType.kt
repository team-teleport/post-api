package com.sungan.postApi.domain.report

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.domain.SoftDeletedBaseEntity
import com.sungan.postApi.dto.ReportTypeVo
import javax.persistence.*

@Entity
class ReportType(
    reportCategory: ReportCategory,
    label: String
): SoftDeletedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var category: ReportCategory = reportCategory

    @Column(nullable = false)
    var label: String = label

    @OneToMany(mappedBy = "reportType")
    var reports: MutableList<Report> = ArrayList()

    fun convertToVo() = ReportTypeVo(
        id,
        category.name,
        label
    )

    companion object {
        val equalsAndHashCodeProperties = arrayOf(ReportType::id)
        val toStringProperties = arrayOf(
            ReportType::id,
            ReportType::category,
            ReportType::label
        )
    }

    override fun equals(other: Any?): Boolean = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)
    override fun toString(): String = kotlinToString(properties = toStringProperties)
}

enum class ReportCategory {
    REQUEST,
    REPORT,
    ETC
}