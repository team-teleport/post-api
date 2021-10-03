package com.sungan.postApi.domain

import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.VehicleVo
import javax.persistence.*

@Entity
class Vehicle(
    @Id
    val name: String,
    val colorCode: String,
    @Enumerated(value = EnumType.STRING)
    val type: VehicleType
) {
    @OneToMany(mappedBy = "vehicle")
    val sungans: MutableList<Sungan> = ArrayList()

    override fun toString() = kotlinToString(properties = Vehicle.toStringProperties)
    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Vehicle::name)
        private val toStringProperties = arrayOf(
            Vehicle::colorCode,
            Vehicle::name,
            Vehicle::type
        )
    }

    fun convertToVo() = VehicleVo(
        colorCode,
        name,
        type.name
    )
}

enum class VehicleType {
    SUBWAY,
    BUS,
    OTHER
}