package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import javax.persistence.*

@Entity
class StationHasTypes(
    station: Line2Station,
    type: Line2Type
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "line2_station_id")
    val station: Line2Station = station

    @ManyToOne
    @JoinColumn(name = "line2_type_id")
    val type: Line2Type = type

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(StationHasTypes::id)
        private val toStringProperties = arrayOf(
            StationHasTypes::id,
            StationHasTypes::station,
            StationHasTypes::type
        )
    }
}