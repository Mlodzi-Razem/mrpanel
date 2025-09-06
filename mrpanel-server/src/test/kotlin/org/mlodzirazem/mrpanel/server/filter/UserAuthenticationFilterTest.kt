package org.mlodzirazem.mrpanel.server.filter

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.mlodzirazem.mrpanel.server.auth.AuthenticatedUser

private const val EMAIL = "EMAIL"
private const val NAME = "NAME"

class UserAuthenticationFilterTest : DescribeSpec({
    describe("doFilter") {
        val filter = UserAuthenticationFilter()

        val res = mockk<ServletResponse>()

        val chain = mockk<FilterChain>() {
            every { doFilter(any(), res) } just Runs
        }

        describe("when attributes set") {
            val req = mockk<HttpServletRequest>() {
                every { getHeader("X-User-Email") } returns EMAIL
                every { getHeader("X-User-Name") } returns NAME
                every { setAttribute(any(), any()) } just Runs
            }

            filter.doFilter(req, res, chain)

            it("sets authenticated user in request") {
                verify(exactly = 1) {
                    req.setAttribute(
                        AuthenticatedUser.REQUEST_ATTRIBUTE,
                        withArg<AuthenticatedUser> { user ->
                            user.email shouldBe EMAIL
                            user.name shouldBe NAME
                        })
                }
            }

            it("chains the request and response") {
                verify(exactly = 1) { chain.doFilter(req, res) }
            }
        }

        describe("when attributes not set") {
            val req = mockk<HttpServletRequest>() {
                every { getHeader("X-User-Email") } returns null
                every { getHeader("X-User-Name") } returns null
            }

            filter.doFilter(req, res, chain)

            it("does not set authenticated user in request") {
                verify(exactly = 0) { req.setAttribute(any(), any()) }
            }

            it("chains the request and response") {
                verify(exactly = 1) { chain.doFilter(req, res) }
            }
        }
    }

})
