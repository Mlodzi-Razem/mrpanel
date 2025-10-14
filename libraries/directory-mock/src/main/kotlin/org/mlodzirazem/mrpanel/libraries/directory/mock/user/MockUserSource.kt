package org.mlodzirazem.mrpanel.libraries.directory.mock.user

import org.mlodzirazem.mrpanel.libraries.directory.api.user.FindUsersRequest
import org.mlodzirazem.mrpanel.libraries.directory.api.user.User
import org.mlodzirazem.mrpanel.libraries.directory.api.user.UserSource
import org.mlodzirazem.mrpanel.libraries.directory.mock.model.DirectoryMock
import org.mlodzirazem.mrpanel.model.Email

class MockUserSource(private val directoryMock: DirectoryMock) : UserSource {
    override fun findUsers(req: FindUsersRequest): Sequence<User> {
        return findAllUsers()
            .filter { req.emailLike == null || it.email.value.lowercase().contains(req.emailLike!!.trim().lowercase()) }
    }

    override fun findAllUsers(): Sequence<User> {
        return (directoryMock.users?.user ?: emptyList()).asSequence().map {
            User(
                id = it.id,
                email = Email(it.email),
                additionalEmails = it.additionalEmails?.email?.map { email -> Email(email) }?.toSet() ?: emptySet()
            )
        }
    }

    override fun findUserByEmail(email: Email): User? {
        return findAllUsers().firstOrNull { it.email == email }
    }
}