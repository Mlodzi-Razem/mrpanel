package org.mlodzirazem.mrpanel.server.auth

import org.mlodzirazem.mrpanel.libraries.directory.api.group.Group
import org.mlodzirazem.mrpanel.libraries.directory.api.group.GroupSource
import org.mlodzirazem.mrpanel.libraries.directory.api.user.UserSource
import org.mlodzirazem.mrpanel.model.Email
import org.mlodzirazem.mrpanel.server.config.CacheNames.USER_AUTH_PROFILE
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

data class UserAuthProfile(
    val email: Email,
    val groups: Set<Group>
)

@Component
class UserAuthProfileFinder(private val groupSource: GroupSource, private val userSource: UserSource) {
    @Cacheable(USER_AUTH_PROFILE, key = "#email.value.trim()")
    fun findProfile(email: Email): UserAuthProfile? {
        val user = userSource.findUserByEmail(email) ?: throw IllegalStateException("User not found for email: ${email.value}")

        return UserAuthProfile(
            email = email,
            groups = groupSource.findGroupsForUserId(user.id).toSet()
        )
    }
}