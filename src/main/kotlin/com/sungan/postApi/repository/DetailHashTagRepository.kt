package com.sungan.postApi.repository

import com.sungan.postApi.domain.DetailHashTag
import org.springframework.data.jpa.repository.JpaRepository

interface DetailHashTagRepository: JpaRepository<DetailHashTag, Long> {
    fun findByName(name: String): DetailHashTag?
}