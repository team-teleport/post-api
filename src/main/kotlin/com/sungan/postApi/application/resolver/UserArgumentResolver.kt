package com.sungan.postApi.application.resolver

import com.fasterxml.jackson.core.JsonProcessingException
import com.sungan.postApi.application.support.SunganError
import com.sungan.postApi.application.support.SunganException
import com.sungan.postApi.application.support.SunganResponse
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
    ): Long {
        val userIdStr: String? = webRequest.getHeader("userId")
        if (userIdStr == null && userIdStr?.length == 0) {
            throw SunganException(SunganError.NOT_LOGIN)
        }
        return userIdStr!!.toLong()
    }

    @ExceptionHandler(JsonProcessingException::class)
    fun jsonException(e: Exception): SunganResponse<Unit> {
        return SunganResponse(HttpStatus.INTERNAL_SERVER_ERROR, "로그인에 실패했습니다.")
    }
}