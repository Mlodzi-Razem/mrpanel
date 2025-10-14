package org.mlodzirazem.mrpanel.libraries.directory.api.group

interface GroupSource {
    fun findGroups(req: FindGroupsRequest): Sequence<Group>
    fun findAllGroups(): Sequence<Group>
    fun findGroupsForUserId(userId: String): Sequence<Group>
}