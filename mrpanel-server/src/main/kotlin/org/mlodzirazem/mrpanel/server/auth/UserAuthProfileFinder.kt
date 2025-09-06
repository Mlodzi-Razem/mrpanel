package org.mlodzirazem.mrpanel.server.auth

import org.mlodzirazem.mrpanel.server.config.CacheNames.USER_AUTH_PROFILE
import org.mlodzirazem.mrpanel.server.model.Email
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

data class UserAuthProfile(
    val email: Email,
    val groups: Set<Email>
)

@Component
class UserAuthProfileFinder(private val userGroupsSource: UserGroupsSource) {
    @Cacheable(USER_AUTH_PROFILE, key = "#email.value.trim()")
    fun findProfile(email: Email): UserAuthProfile? = UserAuthProfile(
        email = email,
        groups = userGroupsSource.findGroups(email)
    )
}