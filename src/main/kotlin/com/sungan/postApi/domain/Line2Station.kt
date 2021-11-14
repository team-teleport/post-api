package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.domain.hotplace.Hotplace
import com.sungan.postApi.domain.sungan.Sungan
import com.sungan.postApi.dto.Line2StationVo
import javax.persistence.*

@Entity
class Line2Station(name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val name: String = name

    @OneToMany(mappedBy = "station")
    var types: MutableList<StationHasTypes> = ArrayList()

    @OneToMany(mappedBy = "station")
    var sungans: MutableList<Sungan> = ArrayList()

    @OneToMany(mappedBy = "station")
    var hotplaces: MutableList<Hotplace> = ArrayList()

    fun convertToVo() = Line2StationVo(
        id!!,
        name,
        types.map { stationHasTypes -> stationHasTypes.type.convertToVo() }
    )

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Line2Station::id)
        private val toStringProperties = arrayOf(
            Line2Station::id,
            Line2Station::name,
            Line2Station::types
        )
    }
}