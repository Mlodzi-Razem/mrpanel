package org.mlodzirazem.mrpanel.server.auth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class AuthResolverTest : DescribeSpec({
    describe("user") {
        beforeTest {
            mockkStatic(RequestContextHolder::getRequestAttributes)
        }
        afterTest {
            unmockkStatic(RequestContextHolder::getRequestAttributes)
        }

        val resolver = AuthResolver()

        it("returns null when there is no request") {
            every { RequestContextHolder.getRequestAttributes() } returns null
            resolver.user shouldBe null
        }

        it("returns authenticated user when there is one") {
            val authenticatedUser = AuthenticatedUser("email", "name")

            every { RequestContextHolder.getRequestAttributes() } returns mockk<ServletRequestAttributes> {
                every { request } returns mockk {
                    every { getAttribute(AuthenticatedUser.REQUEST_ATTRIBUTE) } returns authenticatedUser
                }
            }
            resolver.user shouldBeSameInstanceAs authenticatedUser
        }

    }

    describe("isAuthenticated") {
        val resolver = spyk(AuthResolver())

        it("returns true when user is authenticated") {
            every { resolver.user } returns AuthenticatedUser("email", "name")
            resolver.isAuthenticated shouldBe true
        }

        it("returns false when user is not authenticated") {
            every { resolver.user } returns null
            resolver.isAuthenticated shouldBe false
        }
    }
})
