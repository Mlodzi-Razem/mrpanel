package org.mlodzirazem.mrpanel.server.core

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

/**
 * Provides the current HTTP request in the context of the executing thread.
 *
 * This component is used to access details of the current HTTP request, if one exists.
 * It relies on Spring's `RequestContextHolder` to retrieve the request attributes
 * specific to the execution context. If no request is associated, the value is `null`.
 *
 * It is primarily used in scenarios where request-specific details, such as headers,
 * parameters, or session attributes, are needed within service or component layers.
 *
 * Note: This component should be used cautiously, as it assumes the existence of
 * an active HTTP request context. For use in non-web contexts, ensure that access
 * is safeguarded or handled appropriately to avoid unexpected behavior.
 */
@Component
class RequestProvider {
    val currentRequest: HttpServletRequest? = (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes?)?.request
}