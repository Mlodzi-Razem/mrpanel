package org.mlodzirazem.mrpanel.server.audit.db

import org.springframework.data.jpa.repository.JpaRepository

interface DbAuditLogRepository : JpaRepository<DbAuditLog, Long> {
}