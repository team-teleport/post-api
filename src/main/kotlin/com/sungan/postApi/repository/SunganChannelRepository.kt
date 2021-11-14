package com.sungan.postApi.repository

import com.sungan.postApi.domain.sungan.SunganChannel
import org.springframework.data.jpa.repository.JpaRepository

interface SunganChannelRepository: JpaRepository<SunganChannel, Long> {
}