package com.sooni.postapi.repository

import com.sooni.postapi.domain.Sungan
import com.sooni.postapi.domain.SunganLike
import com.sooni.postapi.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface SunganLikeRepository: JpaRepository<SunganLike, Long> {
    fun findByUserAndSungan(user: User, sungan: Sungan): SunganLike?
}