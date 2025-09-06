package org.mlodzirazem.mrpanel.server.auth

data class AuthenticatedUser(val email: String, val name: String) {
    companion object {
        const val REQUEST_ATTRIBUTE = "MR_AUTH_USER"
    }
}