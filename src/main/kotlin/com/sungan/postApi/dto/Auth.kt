package com.sungan.postApi.dto

data class UserInfoResDto(
    var username: String = "",
    var id: Long = 0,
    var avatar: String? = null,
)

data class UserInfoReqDto(
    val userId: Long,
)