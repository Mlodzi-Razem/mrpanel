package org.mlodzirazem.mrpanel.libraries.directory.api.user

import org.mlodzirazem.mrpanel.model.Email

data class User(
    val id: String,
    val email: Email,
    val additionalEmails: Set<Email>
)
