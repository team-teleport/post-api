package com.sooni.postapi.dto

class Like

data class SunganLikeVo (
    val id: Long,
    val sunganId: Long,
    val user: UserVo
)