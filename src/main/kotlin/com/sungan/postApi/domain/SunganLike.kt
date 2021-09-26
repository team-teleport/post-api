package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.SunganLikeVo
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class SunganLike(
    val userId: Long,

    @ManyToOne
    @JoinColumn(name = "sungan_id")
    val sungan: Sungan
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    override fun toString() = kotlinToString(properties = SunganLike.toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(SunganLike::id)
        private val toStringProperties = arrayOf(
            SunganLike::id,
            SunganLike::userId,
            SunganLike::sungan
        )
    }

    fun convertToVo(): SunganLikeVo =
        SunganLikeVo(this.id!!, this.sungan.id!!, userId)
}