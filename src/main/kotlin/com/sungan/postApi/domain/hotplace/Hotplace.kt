package com.sungan.postApi.domain.hotplace

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.domain.Line2Station
import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.HotplaceVo
import javax.persistence.*

@Entity
class Hotplace(
    @Column var text: String,
    @Embedded
    var userInfo: UserInfo,
    @ManyToOne @JoinColumn(name = "line2_station_id") var station: Line2Station?,
    @Column var place: String,
    @Column var emoji: String?,
) : PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "hotplace")
    var hotplaceLikes: MutableList<HotplaceLike> = ArrayList()

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    fun convertToVo() =
        HotplaceVo(
            id!!,
            text,
            emoji,
            userInfo,
            station?.convertToVo(),
            place,
            hotplaceLikes.size.toLong(),
            createdAt,
            updatedAt
        )

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Hotplace::id)
        private val toStringProperties = arrayOf(
            Hotplace::id,
            Hotplace::station,
            Hotplace::emoji,
            Hotplace::userInfo,
            Hotplace::createdAt,
            Hotplace::updatedAt
        )
    }
}