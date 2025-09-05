package org.mlodzirazem.mrpanel.server.auth

import org.mlodzirazem.mrpanel.server.model.Email

/**
 * Finds
 */
interface UserGroupsSource {
    fun findGroups(email: Email): Set<Email>
}