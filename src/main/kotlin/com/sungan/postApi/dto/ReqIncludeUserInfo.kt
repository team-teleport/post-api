package com.sungan.postApi.dto

import com.sungan.postApi.domain.UserInfo

interface ReqIncludeUserInfo {
    var userName: String
    var userProfileImgUrl: String?

    fun makeUserInfo(userId: Long) = UserInfo(userId, userName, userProfileImgUrl)
}