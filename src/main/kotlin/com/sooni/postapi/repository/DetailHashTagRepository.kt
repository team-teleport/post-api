package com.sooni.postapi.repository

import com.sooni.postapi.domain.DetailHashTag
import org.springframework.data.jpa.repository.JpaRepository

interface DetailHashTagRepository: JpaRepository<DetailHashTag, Long> {
    fun findByName(name: String): DetailHashTag?
}