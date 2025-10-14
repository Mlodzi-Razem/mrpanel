package org.mlodzirazem.mrpanel.libraries.directory.api.group

data class FindGroupsRequest(
    val emailLike: String?,
    val nameLike: String?
)