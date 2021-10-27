package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.ReportVo
import org.hibernate.annotations.ColumnDefault
import javax.persistence.*

@Entity
class Report(
    @Column(nullable = false)
    var reportType: ReportType,
    @Embedded
    var userInfo: UserInfo,
    @Column(nullable = false)
    var shouldBeUploaded: Boolean,
    @Column(nullable = false)
    var vehicleNum: String,
    @Column(nullable = false)
    var carNum: String,
    @Column(nullable = true)
    var detail: String? = null
) : PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    @ColumnDefault("0")
    var readCnt: Long = 0

    @Column
    @ColumnDefault("0")
    var likeCnt: Long = 0

    @OneToMany(mappedBy = "report")
    var likes: MutableList<ReportLike> = ArrayList()

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Report::id)
        private val toStringProperties = arrayOf(
            Report::id,
            Report::reportType,
            Report::vehicleNum,
            Report::carNum,
            Report::likeCnt,
            Report::readCnt,
            Report::shouldBeUploaded
        )
    }

    fun convertToVo() =
        ReportVo(id, reportType, userInfo, vehicleNum, carNum, detail, readCnt, likeCnt, createdAt, updatedAt)
}

enum class ReportType {
    REQUEST,
    REPORT,
    ETC
}