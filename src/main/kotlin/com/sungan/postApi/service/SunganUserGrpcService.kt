package com.sungan.postApi.service

import com.sungan.postApi.domain.User
import com.sungan.userGrpc.UserGrpc
import com.sungan.userGrpc.UserInfoRequest
import com.sungan.userGrpc.UserInfoResponse
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class SunganUserGrpcService {
    @GrpcClient("auth")
    lateinit var userStub: UserGrpc.UserBlockingStub

    fun getUserInfo(userId: Long): User {
        val request = UserInfoRequest.newBuilder().setUserId(userId).build()
        val response: UserInfoResponse = userStub.getUserInfo(request)
        return User(
            id = response.userId,
            username = response.username,
            email = response.email,
            profileImageUrl = response.profileImageUrl,
            avatar = response.avatar
        )
    }
}