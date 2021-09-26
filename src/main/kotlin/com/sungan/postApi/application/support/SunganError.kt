package com.sungan.postApi.application.support

import org.springframework.http.HttpStatus

enum class SunganError(
    val desc: String, val status: HttpStatus
) {
    // 4xx
    DUPLICATE("중복 입력입니다.", HttpStatus.BAD_REQUEST), //400
    BAD_REQUEST_INVALID_ID("해당 ID의 객체가 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    BAD_REQUEST("사용자의 입력이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    NOT_LOGIN("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED), //401
    FORBIDDEN("권한이 없는 유저 입니다.", HttpStatus.FORBIDDEN), //403
    DELETD_USER("삭제된 유저입니다.", HttpStatus.FORBIDDEN),

    // 500
    FAIL_TO_CREATE("db 에러 - 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    ENTITY_NOT_FOUND("db 에러 - 엔터티를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    AWS_ERROR("AWS 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNEXPECTED_VALUE("예상하지 못한 값입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR("알 수 없는 에러", HttpStatus.INTERNAL_SERVER_ERROR),
}