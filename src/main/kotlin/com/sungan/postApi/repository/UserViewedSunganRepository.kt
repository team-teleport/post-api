package com.sungan.postApi.repository

import com.sungan.postApi.domain.sungan.UserViewedSungan
import org.springframework.data.jpa.repository.JpaRepository

interface UserViewedSunganRepository:JpaRepository<UserViewedSungan, Long> {
}