package com.sungan.postApi.repository

import com.sungan.postApi.domain.sungan.Comment
import com.sungan.postApi.domain.sungan.Sungan

interface CommentQueryRepository {
    fun findBySunganOrderByLikes(sungan: Sungan): Comment?
}