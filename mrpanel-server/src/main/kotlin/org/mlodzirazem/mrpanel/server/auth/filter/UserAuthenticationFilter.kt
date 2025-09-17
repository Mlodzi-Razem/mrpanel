package org.mlodzirazem.mrpanel.server.auth.filter

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

        val shouldPassTheUser = email != null && name != null;

        if (shouldPassTheUser) {
            val authenticatedUser = AuthenticatedUser(email, name)
            ScopedValue.where(AuthenticatedUser.SCOPED_VALUE, authenticatedUser).run {
                chain.doFilter(request, response)
            }
        } else {
            chain.doFilter(request, response)
        }
    }
}

