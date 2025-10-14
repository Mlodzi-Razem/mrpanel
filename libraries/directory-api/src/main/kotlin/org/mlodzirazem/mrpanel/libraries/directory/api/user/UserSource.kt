package org.mlodzirazem.mrpanel.libraries.directory.api.user

import org.mlodzirazem.mrpanel.model.Email

interface UserSource {
    fun findUsers(req: FindUsersRequest): Sequence<User>
    fun findAllUsers(): Sequence<User>

    fun findUserByEmail(email: Email): User?
}