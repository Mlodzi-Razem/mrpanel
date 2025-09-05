package org.mlodzirazem.mrpanel.server.auth

import org.mlodzirazem.mrpanel.server.model.Email
import java.util.*

/**
 * To be used only in local development
 */
class MockUserGroupsSource : UserGroupsSource {
    override fun findGroups(email: Email): Set<Email> {
        val properties = Properties().apply {
            load(MockUserGroupsSource::class.java.classLoader.getResourceAsStream("google/mock/groups.properties"))
        }

        return properties.getOrElse(email.value, { "" }).toString().split(',')
            .filter { it.isNotBlank() }
            .map { Email(it) }
            .toSet()
    }
}