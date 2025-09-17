package org.mlodzirazem.mrpanel.server.auth

data class AuthenticatedUser(val email: String, val name: String) {
    companion object {
        val SCOPED_VALUE = ScopedValue.newInstance<AuthenticatedUser?>()!!
    }
}