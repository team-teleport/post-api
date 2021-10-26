package com.sungan.postApi.dto

import java.time.LocalDateTime

abstract class PostBaseVo {
    abstract val createdAt: LocalDateTime
    abstract val updatedAt: LocalDateTime
}