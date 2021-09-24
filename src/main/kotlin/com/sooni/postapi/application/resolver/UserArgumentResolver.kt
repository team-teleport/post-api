package com.sooni.postapi.application.resolver

import com.fasterxml.jackson.core.JsonProcessingException
import com.sooni.postapi.application.support.SunganResponse
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
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterName == "userId"
    }

    @Throws(JsonProcessingException::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Long? = webRequest.getHeader("userId")?.toLong()

    @ExceptionHandler(JsonProcessingException::class)
    fun jsonException(e: Exception): SunganResponse<Unit> {
        return SunganResponse(HttpStatus.INTERNAL_SERVER_ERROR, "로그인에 실패했습니다.")
    }
}