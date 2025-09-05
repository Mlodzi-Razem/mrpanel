package org.mlodzirazem.mrpanel.server.auth

import io.kotest.core.spec.style.FunSpec
import org.hamcrest.Matchers.equalTo
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

private const val USER_EMAIL = "user@email.com"
private const val USER_NAME = "User Name"

@WebMvcTest(AuthResolverE2ETestController::class)
@Import(AuthResolver::class)
class AuthResolverE2ETest(private val mockMvc: MockMvc) : FunSpec({
    test("is authenticated") {
        mockMvc.get("") {
            header("X-User-Email", USER_EMAIL)
            header("X-User-Name", USER_NAME)
        }.andExpect {
            content {
                jsonPath("email", equalTo(USER_EMAIL))
                jsonPath("name", equalTo(USER_NAME))
            }
        }
    }

    test("is not authenticated") {
        mockMvc.get("").andExpect {
            content { string("{}") }
        }
    }
})

@RestController
class AuthResolverE2ETestController(private val authResolver: AuthResolver) {
    @GetMapping
    fun test() = authResolver.user ?: "{}"
}