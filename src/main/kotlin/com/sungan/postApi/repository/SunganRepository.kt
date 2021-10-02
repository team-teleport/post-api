package com.sungan.postApi.repository

import com.sungan.postApi.domain.Sungan
import org.springframework.data.jpa.repository.JpaRepository

interface SunganRepository: JpaRepository<Sungan, Long>, SunganQueryRepository{
}