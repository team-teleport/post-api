package com.sooni.postapi.repository

import com.sooni.postapi.domain.MainHashTag
import org.springframework.data.jpa.repository.JpaRepository

interface MainHashTagRepository: JpaRepository<MainHashTag, Long> {
}