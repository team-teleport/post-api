package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.HotplaceVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Hotplace(
    @Column var title: String,
    @Column var text: String,
    @Column var userId: Long,
    @ManyToOne @JoinColumn(name = "line2_station_id") var station: Line2Station,
    @Column var place: String
): PostBaseEntity() {
    @Id
    val id: Long? = null

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    fun convertToVo() = HotplaceVo(id!!, title, text, userId, station.convertToVo(), place, createdAt, updatedAt)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Hotplace::id)
        private val toStringProperties = arrayOf(
            Hotplace::id,
            Hotplace::station,
            Hotplace::title,
            Hotplace::userId,
            Hotplace::createdAt,
            Hotplace::updatedAt
        )
    }
}