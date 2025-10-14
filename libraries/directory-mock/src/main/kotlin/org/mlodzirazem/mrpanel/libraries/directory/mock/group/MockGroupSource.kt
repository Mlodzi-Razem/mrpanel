package org.mlodzirazem.mrpanel.libraries.directory.mock.group

import org.mlodzirazem.mrpanel.libraries.directory.api.group.FindGroupsRequest
import org.mlodzirazem.mrpanel.libraries.directory.api.group.Group
import org.mlodzirazem.mrpanel.libraries.directory.api.group.GroupSource
import org.mlodzirazem.mrpanel.libraries.directory.mock.model.DirectoryMock
import org.mlodzirazem.mrpanel.libraries.directory.mock.model.GroupMock
import org.mlodzirazem.mrpanel.model.Email

class MockGroupSource(private val directoryMock: DirectoryMock) : GroupSource {

    override fun findGroups(req: FindGroupsRequest): Sequence<Group> {
        return findAllGroups()
            .filter { req.emailLike == null || it.email.value.lowercase().contains(req.emailLike!!.lowercase().trim()) }
            .filter { req.nameLike == null || it.name.lowercase().contains(req.nameLike!!.lowercase().trim()) }
    }

    override fun findAllGroups(): Sequence<Group> {
        return (directoryMock.groups?.group ?: emptyList()).asSequence().map(GROUP_MAPPER)
    }

    override fun findGroupsForUserId(userId: String): Sequence<Group> {
        return directoryMock.groups?.group?.filter { it.members?.id?.contains(userId) ?: false }
            ?.asSequence()
            ?.map(GROUP_MAPPER)
            ?: emptySequence()
    }

    companion object {
        private val GROUP_MAPPER: (GroupMock) -> Group = {
            Group(
                email = Email(it.email),
                name = it.name
            )
        }
    }
}