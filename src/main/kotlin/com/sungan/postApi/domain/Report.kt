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
    @ManyToOne
    @JoinColumn(name = "line2_station_id")
    var station: Line2Station,
    @Column(nullable = false)
    var userId: Long,
    @Column(nullable = false)
    var shouldBeUploaded: Boolean,
    @Column(nullable = true)
    var detail: String? = null,
) {
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

    override fun toString() = kotlinToString(properties = Report.toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = Report.equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = Report.equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Report::id)
        private val toStringProperties = arrayOf(
            Report::id,
            Report::reportType,
            Report::station,
            Report::likeCnt,
            Report::readCnt,
            Report::shouldBeUploaded
        )
    }
    fun convertToVo() = ReportVo(id, reportType, userId, detail, station.convertToVo(), readCnt, likeCnt)
}

enum class ReportType {
    REQUEST,
    REPORT,
    ETC
}