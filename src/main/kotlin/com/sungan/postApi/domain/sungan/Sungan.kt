package com.sungan.postApi.domain.sungan

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.SunganPreview
import com.sungan.postApi.dto.SunganVo
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE sungan SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(
    indexes = [
        Index(name = "station_errand_id_idx", columnList = "line2_station_id, deleted"),
        Index(name = "channel_errand_idx", columnList = "sungan_channel_id")
    ]
)
class Sungan(
    @Column(nullable = false)
    var text: String,
    @Embedded
    var userInfo: UserInfo,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "line2_station_id")
    var station: Line2Station?,
    @Column
    var emoji: String?,
    @ManyToOne
    @JoinColumn(name = "sungan_channel_id")
    var sunganChannel: SunganChannel
) : PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "sungan", cascade = [CascadeType.REMOVE])
    var contents: MutableList<SunganContent> = ArrayList()

    @Column
    @ColumnDefault("0")
    var readCnt: Long = 0

    @Column
    @ColumnDefault("0")
    var likeCnt: Long = 0

    @OneToMany(mappedBy = "sungan", cascade = [CascadeType.REMOVE])
    val likes: MutableList<SunganLike> = ArrayList()

    @OneToMany(mappedBy = "sungan", cascade = [CascadeType.REMOVE])
    val comments: MutableList<Comment> = ArrayList()

    @OneToMany(mappedBy = "sungan")
    val viewedUsers: MutableList<UserViewedSungan> = ArrayList()

    fun convertToVo(): SunganVo =
        SunganVo(
            id!!,
            station?.convertToVo(),
            sunganChannel,
            text,
//            contents.asSequence().map { content -> content.convertToVo() }.toList(),
            emoji,
            userInfo,
            comments.asSequence().map { comment -> comment.convertToVo() }.toList(),
            readCnt,
            likeCnt,
            createdAt,
            updatedAt
        )

    fun convertToPreview() = SunganPreview(
        id!!,
        station?.convertToVo(),
        sunganChannel,
        text,
        emoji,
        userInfo, readCnt, likeCnt, createdAt, updatedAt
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
