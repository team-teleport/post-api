package com.sungan.postApi.repository

import com.sungan.postApi.domain.Comment
import com.sungan.postApi.domain.Sungan
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findBySungan(sungan: Sungan): MutableList<Comment>
}