package com.sungan.postApi.domain.sungan

import au.com.console.kassava.kotlinToString
import javax.persistence.*

@Entity
@Table(
    indexes = [
        Index(name = "channel_idx", columnList = "name")
    ]
)
class SunganChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    lateinit var name: String

    companion object {
        private val toStringProperties = arrayOf(
            SunganChannel::id,
            SunganChannel::name
        )
    }

    override fun toString(): String = kotlinToString(properties = toStringProperties)
}