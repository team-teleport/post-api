package com.sungan.postApi.domain

import au.com.console.kassava.kotlinToString
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class SunganChannel {
    @Id
    @GeneratedValue
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