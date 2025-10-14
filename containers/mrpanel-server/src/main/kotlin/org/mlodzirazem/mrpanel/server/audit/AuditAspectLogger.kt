package org.mlodzirazem.mrpanel.server.audit

import org.mlodzirazem.mrpanel.server.audit.db.DbAuditLog
import org.mlodzirazem.mrpanel.server.audit.db.DbAuditLogRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * Handles the logging of audit information into the database.
 *
 * This component is responsible for persisting audit logs to the `audit_log` table.
 * It operates within a new transactional context to ensure that audit
 * logs are recorded independently of the surrounding transaction.
 *
 * @constructor
 * @param dbAuditLogRepository Repository to handle persistence of `DbAuditLog` entities.
 */
@Component
class AuditAspectLogger(private val dbAuditLogRepository: DbAuditLogRepository) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun logOperation(context: AuditContext) {
        val log = DbAuditLog().apply {
            memberId = context.memberId
            operationId = context.operationId
            payload = context.payload
            url = context.url
            exception = context.exception?.stackTraceToString()
        }

        dbAuditLogRepository.save(log)
    }
}