package com.sooni.postapi.repository

import com.sooni.postapi.domain.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository: JpaRepository<Vehicle, Long> {
}