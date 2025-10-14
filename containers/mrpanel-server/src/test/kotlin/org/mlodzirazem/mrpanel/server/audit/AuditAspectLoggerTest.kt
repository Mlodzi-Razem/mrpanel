package org.mlodzirazem.mrpanel.server.audit

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.mlodzirazem.mrpanel.server.audit.db.DbAuditLog
import org.mlodzirazem.mrpanel.server.audit.db.DbAuditLogRepository

class AuditAspectLoggerTest : DescribeSpec({

    describe("logOperation") {
        val saveSlot = slot<DbAuditLog>()
        val repository = mockk<DbAuditLogRepository>() {
            every { save(capture(saveSlot)) } returnsArgument 0
        }
        val auditAspectLogger = AuditAspectLogger(repository)

        it("copies attributes from context") {
            val context = AuditContext(
                memberId = 1L,
                operationId = "operationId",
                payload = "payload",
                url = "url",
                exception = Exception("exception")
            )

            auditAspectLogger.logOperation(context)

            verify(exactly = 1) { repository.save(saveSlot.captured) }
            confirmVerified(repository)

            with(saveSlot.captured) {
                memberId shouldBe context.memberId
                operationId shouldBe context.operationId
                payload shouldBe context.payload
                url shouldBe context.url
                exception shouldBe context.exception?.stackTraceToString()
            }
        }
    }
})
