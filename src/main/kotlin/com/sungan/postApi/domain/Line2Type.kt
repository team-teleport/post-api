package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.Line2TypeVo
import javax.persistence.*

@Entity
class Line2Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column
    lateinit var lineType: String

    @OneToMany(mappedBy = "type")
    var stations: MutableList<StationHasTypes> = ArrayList()

    fun convertToVo() = Line2TypeVo(id!!, lineType)

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Line2Type::id)
        private val toStringProperties = arrayOf(
            Line2Type::id,
            Line2Type::lineType
        )
    }
}