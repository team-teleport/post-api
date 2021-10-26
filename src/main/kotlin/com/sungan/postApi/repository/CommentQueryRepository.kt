package com.sungan.postApi.repository

import com.sungan.postApi.domain.Comment
import com.sungan.postApi.domain.Sungan

interface CommentQueryRepository {
    fun findBySunganOrderByLikes(sungan: Sungan): Comment?
}