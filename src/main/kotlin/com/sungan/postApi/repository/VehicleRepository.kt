package com.sungan.postApi.repository

import com.sungan.postApi.domain.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository: JpaRepository<Vehicle, Long> {
}