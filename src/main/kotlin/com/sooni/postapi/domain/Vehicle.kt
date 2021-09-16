package com.sooni.postapi.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Vehicle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String, // "Bus"
    val type: VehicleType,
    val icon: String,
    val local_icon: String
)

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