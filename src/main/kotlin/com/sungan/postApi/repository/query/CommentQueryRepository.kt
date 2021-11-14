package com.sungan.postApi.repository.query

import com.sungan.postApi.domain.sungan.Comment
import com.sungan.postApi.domain.sungan.Sungan

interface CommentQueryRepository {
    fun findBySunganOrderByLikes(sungan: Sungan): Comment?
}