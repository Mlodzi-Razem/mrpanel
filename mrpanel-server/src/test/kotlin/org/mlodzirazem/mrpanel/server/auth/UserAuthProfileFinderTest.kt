package org.mlodzirazem.mrpanel.server.auth

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.mlodzirazem.mrpanel.server.model.Email
import org.springframework.boot.autoconfigure.cache.CacheType
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache
import org.springframework.context.annotation.Import

/**
 * Uses Spring container because of the @Cacheable annotation
 */
@AutoConfigureCache(cacheProvider = CacheType.SIMPLE)
@Import(UserAuthProfileFinder::class)
class UserAuthProfileFinderTest(
    private val userAuthProfileFinder: UserAuthProfileFinder,
    @MockkBean private val groupsSource: UserGroupsSource
) : DescribeSpec({
    describe("findProfile") {
        it("returns profile from groups source") {
            val userAuthProfile = UserAuthProfile(
                email = Email("EMAIL@email.com"),
                groups = setOf(Email("GROUP1@domain.com"), Email("GROUP2@domain.com"))
            )

            every { groupsSource.findGroups(userAuthProfile.email) } returns userAuthProfile.groups

            repeat(32) {
                userAuthProfileFinder.findProfile(userAuthProfile.email) shouldBe userAuthProfile
            }
        }
    }
}) {
    override fun extensions() = listOf(SpringExtension)
}
