package com.sooni.postapi.dto

class Like

data class LikeVo (
    val id: Long,
    val sunganId: Long,
    val user: UserVo
)