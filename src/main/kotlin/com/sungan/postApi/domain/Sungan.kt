package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.SunganVo
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Sungan(
    @Column(nullable = false)
    var text: String,
    @Embedded
    var userInfo: UserInfo,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "line2_station_id", nullable = false)
    var station: Line2Station,
    @Column
    var emoji: String?,
    @ManyToOne
    @JoinColumn(name = "sungan_channel_id")
    var sunganChannel: SunganChannel
): PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "sungan")
    var contents: MutableList<SunganContent> = ArrayList()

    @Column
    @ColumnDefault("0")
    var readCnt: Long = 0

    @Column
    @ColumnDefault("0")
    var likeCnt: Long = 0

    @OneToMany(mappedBy = "sungan")
    val comments: MutableList<Comment> = ArrayList()

    @OneToMany(mappedBy = "sungan")
    val viewedUsers: MutableList<UserViewedSungan> = ArrayList()

    fun convertToVo(): SunganVo =
        SunganVo(
            id!!,
            station.convertToVo(),
            sunganChannel,
            text,
            contents.asSequence().map { content -> content.convertToVo() }.toList(),
            emoji,
            userInfo,
            comments.asSequence().map { comment -> comment.convertToVo() }.toList(),
            readCnt,
            likeCnt,
            createdAt,
            updatedAt
        )

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Sungan::id)
        private val toStringProperties = arrayOf(
            Sungan::id,
            Sungan::station,
            Sungan::userInfo,
            Sungan::createdAt,
            Sungan::updatedAt
        )
    }
}
