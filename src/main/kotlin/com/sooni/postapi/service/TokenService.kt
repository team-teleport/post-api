package com.sooni.postapi.service

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.util.FileCopyUtils
import java.lang.reflect.TypeVariable
import java.nio.charset.Charset
import java.nio.file.Files
import java.security.KeyFactory
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField
import java.util.*

@Component
class TokenService {
    private val objectMapper = jacksonObjectMapper()

    final val cpr = ClassPathResource("public_key.pem")

    val publicKeyPEM = String(Files.readAllBytes(cpr.file.toPath()), Charset.defaultCharset())
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace(System.lineSeparator(), "")
        .replace("-----END PUBLIC KEY-----", "")

    @Throws(JsonProcessingException::class)
    fun decodeToken(token: String): TokenPayload {
        val claims: Claims = Jwts.parser()
            .setSigningKey(getPublicKey(publicKeyPEM))
            .parseClaimsJws(token)
            .body
        return TokenPayload(
            claims.get("user_id", Integer::class.java).toLong(),
            claims.get("expires_at", String::class.java).replace(" UTC", "").toLocalDateTime()!!
        )
    }

    fun String.toLocalDateTime(): LocalDateTime? {
        if (this.isNullOrBlank()) return null

        return try {
            val formatter = DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss") // #1
                .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 9, true) // #2
                .toFormatter()
            return LocalDateTime.parse(this, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    fun isTokenExpired(payload: TokenPayload): Boolean {
        val expiration: LocalDateTime = payload.expiredAt
        return expiration.isBefore(LocalDateTime.now())
    }

    fun getPublicKey(str: String): RSAPublicKey {
        val bytes = Base64.getDecoder().decode(str)
        val spec = X509EncodedKeySpec(bytes)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePublic(spec) as RSAPublicKey
    }

}


data class TokenPayload(
    @get:JsonProperty("user_id")
    val userId: Long,
    @get:JsonProperty("expired_at")
    val expiredAt: LocalDateTime
)