package com.sooni.postapi.repository

import com.sooni.postapi.domain.Sungan
import org.springframework.data.jpa.repository.JpaRepository

interface SunganRepository: JpaRepository<Sungan, Long> {
}