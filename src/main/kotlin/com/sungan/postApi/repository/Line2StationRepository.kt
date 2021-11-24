package com.sungan.postApi.repository

import com.sungan.postApi.domain.Line2Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface Line2StationRepository: JpaRepository<Line2Station, Long> {
    @Query(value = "select st from Line2Station st where st.name = :name")
    fun findByName(@Param("name") name: String): Line2Station?
}