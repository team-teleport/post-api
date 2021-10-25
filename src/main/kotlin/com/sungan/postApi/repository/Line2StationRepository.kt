package com.sungan.postApi.repository

import com.sungan.postApi.domain.Line2Station
import org.springframework.data.jpa.repository.JpaRepository

interface Line2StationRepository: JpaRepository<Line2Station, Long> {
    fun findByName(name: String): Line2Station?
}