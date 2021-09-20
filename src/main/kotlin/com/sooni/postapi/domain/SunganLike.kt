package com.sooni.postapi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class SunganLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "sungan_id")
    val sungan: Sungan
) {

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    override fun toString() = kotlinToString(properties = SunganLike.toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(SunganLike::id)
        private val toStringProperties = arrayOf(
            SunganLike::id,
            SunganLike::user,
            SunganLike::sungan
        )
    }
}