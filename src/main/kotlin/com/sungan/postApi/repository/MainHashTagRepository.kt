package com.sungan.postApi.repository

import com.sungan.postApi.domain.MainHashTag
import org.springframework.data.jpa.repository.JpaRepository

interface MainHashTagRepository: JpaRepository<MainHashTag, Long> {
}