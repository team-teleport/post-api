package com.sungan.postApi.domain

import au.com.console.kassava.kotlinToString
import javax.persistence.*

@Entity
class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String, // "Bus"

    @Enumerated
    val type: VehicleType,
    val icon: String,
    val local_icon: String
) {
    override fun toString() = kotlinToString(properties = Vehicle.toStringProperties)
    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Vehicle::id)
        private val toStringProperties = arrayOf(
            Vehicle::id,
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