package com.sungan.postApi.domain.report

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.ReportVo
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE report SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
class Report(
    @ManyToOne
    @JoinColumn(name = "report_type_id")
    var reportType: ReportType,
    @Embedded
    var userInfo: UserInfo,
    @Column(nullable = false)
    var shouldBeUploaded: Boolean,
    @Column
    var vehicleNum: String? = null,
    @Column(nullable = true)
    var detail: String? = null
) : PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    @ColumnDefault("0")
    var readCnt: Long = 0

    @OneToMany(mappedBy = "report", cascade = [CascadeType.REMOVE])
    var likes: MutableList<ReportLike> = ArrayList()

    @OneToMany(mappedBy = "report", cascade = [CascadeType.REMOVE])
    var comments: MutableList<ReportComment> = ArrayList()

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Report::id)
        private val toStringProperties = arrayOf(
            Report::id,
            Report::reportType,
            Report::vehicleNum,
            Report::readCnt,
            Report::shouldBeUploaded
        )
    }

    fun convertToVo() =
        ReportVo(
            id,
            reportType.convertToVo(),
            userInfo,
            vehicleNum,
            detail,
            readCnt,
            likes.size.toLong(),
            createdAt,
            updatedAt
        )
}