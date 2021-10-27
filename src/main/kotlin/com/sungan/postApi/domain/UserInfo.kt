package com.sungan.postApi.domain

import au.com.console.kassava.kotlinToString
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class UserInfo(
    @Column(nullable = false)
    var userId: Long,
    @Column(nullable = false)
    var userName: String,
    @Column
    var userProfileImgUrl: String?
) {
    companion object {
        val toStringProperties = arrayOf(
            UserInfo::userId,
            UserInfo::userName,
            UserInfo::userProfileImgUrl
        )
    }

    override fun toString(): String = kotlinToString(toStringProperties)
}