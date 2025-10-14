package org.mlodzirazem.mrpanel.server.audit

import org.mlodzirazem.mrpanel.server.core.RequestProvider
import org.springframework.stereotype.Component


@ConsistentCopyVisibility
data class AuditContext internal constructor(
    val memberId: Long?,
    val operationId: String,
    val payload: String?,
    val url: String?,
    val exception: Throwable? = null,
)

@Component
class AuditContextCreator(private val requestProvider: RequestProvider, private val memberRetriever: MemberRetriever) {
    fun createContext(operationId: String, exception: Throwable?): AuditContext {
        val request = requestProvider.currentRequest

        return AuditContext(
            memberId = memberRetriever.retrieveMemberId(request?.session),
            operationId = operationId,
            payload = request?.inputStream?.reader()?.readText(),
            url = request?.requestURL?.toString(),
            exception = exception
        )
    }
}