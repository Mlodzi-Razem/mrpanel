package org.mlodzirazem.mrpanel.server.auth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.mlodzirazem.mrpanel.server.model.Email

class MockUserGroupsSourceTest : DescribeSpec({
    describe("findGroups") {
        it("returns empty set when no such email") {
            MockUserGroupsSource().findGroups(Email("NOT_EXISTING@email.com")) shouldBe emptySet()
        }

        it("returns groups for test user") {
            MockUserGroupsSource().findGroups(Email("test.user@do-not-modify.me")) shouldBe setOf(
                "group1@domain.com",
                "group2@domain.com"
            ).map { Email(it) }
        }
    }
})
