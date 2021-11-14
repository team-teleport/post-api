package com.sungan.postApi.repository

import com.sungan.postApi.domain.sungan.Comment
import com.sungan.postApi.domain.sungan.Sungan
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long>, CommentQueryRepository {
    fun findBySungan(sungan: Sungan): MutableList<Comment>
}