package com.sungan.postApi.domain

class User (
    val id: Long,
    val username: String,
    val email: String,
    val profileImageUrl: String,
    val avatar: String,
) {
    fun convertToUserInfo() = UserInfo(id, username, profileImageUrl)
}