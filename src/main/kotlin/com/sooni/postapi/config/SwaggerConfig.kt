package com.sooni.postapi.config

import com.sooni.postapi.domain.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
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
import java.time.LocalDate
import java.time.LocalTime


@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .ignoredParameterTypes(User::class.java)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .build()
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))

    private fun apiInfo(): ApiInfo = ApiInfoBuilder()
        .title("Sungan CRUD API Swagger")
        .description("순간 글쓰기 관련 API")
        .build()

    private fun apiKey(): ApiKey = ApiKey(
        "JWT", "Authorization", "header"
    )

    private fun defaultAuth(): List<SecurityReference?> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("AccessToken", authorizationScopes))
    }

    private fun securityContext(): SecurityContext? = SecurityContext.builder()
        .securityReferences(defaultAuth()).build()
}