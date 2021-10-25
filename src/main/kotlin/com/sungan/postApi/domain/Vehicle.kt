package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.VehicleVo
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

//@Entity
class Vehicle(
//    @Id
    val name: String,
    val colorCode: String,
    @Enumerated(value = EnumType.STRING)
    val type: VehicleType
) {
//    @OneToMany(mappedBy = "vehicle")
//    val sungans: MutableList<Sungan> = ArrayList()

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

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