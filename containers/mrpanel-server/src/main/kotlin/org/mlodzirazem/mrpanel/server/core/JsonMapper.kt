package org.mlodzirazem.mrpanel.server.core

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class JsonMapper {
    val objectMapper = ObjectMapper().findAndRegisterModules()!!

    fun encode(value: Any): String = objectMapper.writeValueAsString(value)
    fun <T : Any> decode(value: String, klass: KClass<T>): T = objectMapper.readValue(value, klass.java)
}

inline fun <reified T : Any> JsonMapper.decode(value: String): T = decode(value, T::class)