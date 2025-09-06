package org.mlodzirazem.mrpanel.server.auth

import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * Resolves authentication information for the current HTTP request.
 *
 * This class provides functionality to access the authenticated user
 * associated with the current request and to determine whether the
 * user is authenticated.
 */
@Component
class AuthResolver {
    val user: AuthenticatedUser?
        get() {
            return ((RequestContextHolder.getRequestAttributes() ?: return null) as ServletRequestAttributes)
                .request.getAttribute(AuthenticatedUser.REQUEST_ATTRIBUTE) as AuthenticatedUser?
        }

    val isAuthenticated get() = user != null

}