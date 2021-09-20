package com.sooni.postapi.repository

import com.sooni.postapi.domain.Sungan
import com.sooni.postapi.domain.SunganContent
import org.springframework.data.jpa.repository.JpaRepository

interface SunganContentsRepository: JpaRepository<SunganContent, Long> {
    fun findBySungan(sungan: Sungan): MutableList<SunganContent>
}