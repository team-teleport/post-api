package com.sooni.postapi.repository

import com.sooni.postapi.domain.Comment
import com.sooni.postapi.domain.Sungan
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findBySungan(sungan: Sungan): MutableList<Comment>
}