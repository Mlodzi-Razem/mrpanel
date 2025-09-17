package org.mlodzirazem.mrpanel.server.filter

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.mlodzirazem.mrpanel.server.auth.AuthenticatedUser
import org.mlodzirazem.mrpanel.server.auth.filter.UserAuthenticationFilter

private const val EMAIL = "EMAIL"
private const val NAME = "NAME"

class UserAuthenticationFilterTest : DescribeSpec({
    describe("doFilter") {
        val filter = UserAuthenticationFilter()

        val res = mockk<ServletResponse>()
        var scopedAuthenticatedUser: AuthenticatedUser? = null

        val chain = mockk<FilterChain> {
            every { doFilter(any(), res) } answers {
                val scopedValue = AuthenticatedUser.SCOPED_VALUE
                scopedAuthenticatedUser = if (scopedValue.isBound) scopedValue.get() else null
                null
            }
        }

        describe("when attributes set") {
            val req = mockk<HttpServletRequest> {
                every { getHeader("X-User-Email") } returns EMAIL
                every { getHeader("X-User-Name") } returns NAME
                every { setAttribute(any(), any()) } just Runs
            }

            filter.doFilter(req, res, chain)

            it("sets authenticated user in request") {
                scopedAuthenticatedUser shouldBe AuthenticatedUser(email = EMAIL, name = NAME)
            }

            it("chains the request and response") {
                verify(exactly = 1) { chain.doFilter(req, res) }
            }
        }

        describe("when attributes not set") {
            val req = mockk<HttpServletRequest> {
                every { getHeader("X-User-Email") } returns null
                every { getHeader("X-User-Name") } returns null
            }

            filter.doFilter(req, res, chain)

            it("does not set authenticated user in request") {
                scopedAuthenticatedUser shouldBe null
            }

            it("chains the request and response") {
                verify(exactly = 1) { chain.doFilter(req, res) }
            }
        }
    }

})
