package org.mlodzirazem.mrpanel.server.audit

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Component

@Component
class MemberRetriever {
    fun retrieveMemberId(session: HttpSession?): Long? = null
}
