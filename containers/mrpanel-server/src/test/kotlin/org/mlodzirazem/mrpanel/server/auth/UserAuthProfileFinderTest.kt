package org.mlodzirazem.mrpanel.server.auth

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.mockk.every
import org.mlodzirazem.mrpanel.libraries.directory.api.group.Group
import org.mlodzirazem.mrpanel.libraries.directory.api.group.GroupSource
import org.mlodzirazem.mrpanel.libraries.directory.api.user.User
import org.mlodzirazem.mrpanel.libraries.directory.api.user.UserSource
import org.mlodzirazem.mrpanel.model.Email
import org.springframework.boot.autoconfigure.cache.CacheType
import org.springframework.boot.cache.test.autoconfigure.AutoConfigureCache
import org.springframework.context.annotation.Import

/**
 * Uses Spring container because of the @Cacheable annotation
 */
@ApplyExtension(SpringExtension::class)
@AutoConfigureCache(cacheProvider = CacheType.SIMPLE)
@Import(UserAuthProfileFinder::class)
class UserAuthProfileFinderTest(
    private val userAuthProfileFinder: UserAuthProfileFinder,
    @MockkBean private val groupsSource: GroupSource,
    @MockkBean private val userSource: UserSource
) : DescribeSpec({
    describe("findProfile") {
        it("returns profile from groups source") {
            val userAuthProfile = UserAuthProfile(
                email = Email("EMAIL@email.com"),
                groups = setOf(
                    Group(email = Email("GROUP1@domain.com"), name = "GROUP1"),
                    Group(email = Email("GROUP2@domain.com"), name = "GROUP2")
                )
            )

            every { userSource.findUserByEmail(userAuthProfile.email) } returns User(
                id = "1",
                email = userAuthProfile.email,
                additionalEmails = emptySet()
            )
            every { groupsSource.findGroupsForUserId("1") } returns userAuthProfile.groups.asSequence()

            repeat(32) {
                userAuthProfileFinder.findProfile(userAuthProfile.email)!! shouldBeEqualUsingFields userAuthProfile
            }
        }
    }
})