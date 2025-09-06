package org.mlodzirazem.mrpanel.server.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.mlodzirazem.mrpanel.server.auth.AuthenticatedUser
import org.springframework.stereotype.Component

@Component
class UserAuthenticationFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val req = request as HttpServletRequest;

        val email = req.getHeader("X-User-Email")
        val name = req.getHeader("X-User-Name")

        if (email != null && name != null) {
            val authenticatedUser = AuthenticatedUser(email, name)
            request.setAttribute(AuthenticatedUser.REQUEST_ATTRIBUTE, authenticatedUser)
        }

        chain.doFilter(request, response)
    }
}

