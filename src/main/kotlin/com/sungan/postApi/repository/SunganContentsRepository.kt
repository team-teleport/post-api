package com.sungan.postApi.repository

import com.sungan.postApi.domain.sungan.Sungan
import com.sungan.postApi.domain.sungan.SunganContent
import org.springframework.data.jpa.repository.JpaRepository

interface SunganContentsRepository: JpaRepository<SunganContent, Long> {
    fun findBySungan(sungan: Sungan): MutableList<SunganContent>
}