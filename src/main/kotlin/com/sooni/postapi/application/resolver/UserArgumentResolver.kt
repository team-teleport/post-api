package com.sooni.postapi.application.resolver

import com.fasterxml.jackson.core.JsonProcessingException
import com.sooni.postapi.application.support.SunganError
import com.sooni.postapi.application.support.SunganException
import com.sooni.postapi.application.support.SunganResponse
import com.sooni.postapi.domain.User
import com.sooni.postapi.repository.UserRepository
import com.sooni.postapi.service.TokenPayload
import com.sooni.postapi.service.TokenService
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserArgumentResolver(
    val tokenService: TokenService,
    val userRepository: UserRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == User::class.java
    }

    @Throws(JsonProcessingException::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User? {
        val token = webRequest.getHeader("Authorization") ?: return null
        val payload: TokenPayload = tokenService.decodeToken(token)
        if (tokenService.isTokenExpired(payload)) return null
        val user =
            userRepository.findById(payload.userId).orElseThrow { throw SunganException(SunganError.ENTITY_NOT_FOUND) }
        return user
    }

    @ExceptionHandler(JsonProcessingException::class)
    fun jsonException(e: Exception): SunganResponse<Unit> {
        return SunganResponse(HttpStatus.INTERNAL_SERVER_ERROR, "로그인에 실패했습니다.")
    }
}