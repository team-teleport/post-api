package com.sungan.postApi.domain

import au.com.console.kassava.kotlinToString
import javax.persistence.*

@Entity
class Vehicle(
    @Id
    @Column(unique = true)
    val colorCode: String,
    @Column(unique = true)
    val name: String,
    @Enumerated
    val type: VehicleType
) {
    override fun toString() = kotlinToString(properties = Vehicle.toStringProperties)
    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Vehicle::colorCode)
        private val toStringProperties = arrayOf(
            Vehicle::colorCode,
            Vehicle::name
        )
    }
}

enum class VehicleType {
    RAIL,
    METRO_RAIL,
    SUBWAY,
    TRAM,
    MONORAIL,
    HEAVY_RAIL,
    COMMUTER_TRAIN,
    HIGH_SPEED_TRAIN,
    LONG_DISTANCE_TRAIN,
    BUS,
    INTERCITY_BUS,
    TROLLEYBUS,
    SHARE_TAXI,
    FERRY,
    CABLE_CAR,
    GONDOLA_LIFT,
    FUNICULAR,
    OTHER
}