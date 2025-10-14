package org.mlodzirazem.mrpanel.server.auth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.spyk

class AuthResolverTest : DescribeSpec({
    describe("user") {
        val resolver = AuthResolver()

        it("returns null when there is no user") {
            resolver.user shouldBe null
        }

        it("returns authenticated user when there is one") {
            val authenticatedUser = AuthenticatedUser("email", "name")

            ScopedValue.where(AuthenticatedUser.SCOPED_VALUE, authenticatedUser).run {
                resolver.user shouldBeSameInstanceAs authenticatedUser
            }
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
