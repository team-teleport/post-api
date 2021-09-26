package com.sungan.postApi.repository

import com.sungan.postApi.domain.Sungan
import com.sungan.postApi.domain.SunganContent
import org.springframework.data.jpa.repository.JpaRepository

interface SunganContentsRepository: JpaRepository<SunganContent, Long> {
    fun findBySungan(sungan: Sungan): MutableList<SunganContent>
}