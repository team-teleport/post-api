package com.sooni.postapi.config

import com.sooni.postapi.domain.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.OAS_30)
            .ignoredParameterTypes(User::class.java)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sooni.postapi.controller"))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))

    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title("Sungan API Swagger")
        .description("순간이동 - 순간 관련 API 문서")
        .build()

    private fun apiKey(): ApiKey = ApiKey(
        "Authorization", "Authorization", "header"
    )

    private fun defaultAuth(): List<SecurityReference?> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("Authorization", authorizationScopes)) // 키 이름이랑 동일하게
    }

    private fun securityContext(): SecurityContext? = SecurityContext.builder()
        .securityReferences(defaultAuth()).build()
}