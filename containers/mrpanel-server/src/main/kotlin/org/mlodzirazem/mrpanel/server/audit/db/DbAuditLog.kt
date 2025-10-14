package org.mlodzirazem.mrpanel.server.audit.db

import jakarta.persistence.*
import org.mlodzirazem.mrpanel.server.db.BaseEntity

@Entity
@Table(name = "audit_log")
@SequenceGenerator(name = BaseEntity.SEQUENCE_NAME, sequenceName = "seq_audit_log_id", allocationSize = 50, initialValue = 0)
class DbAuditLog : BaseEntity() {
    @Column(name = "member_id", nullable = true, updatable = false)
    var memberId: Long? = null

    @Column(nullable = false, name = "operation_id", updatable = false)
    var operationId: String? = null

    @Column(updatable = false, columnDefinition = "jsonb")
    var payload: String? = null

    @Column(nullable = true, updatable = false)
    var url: String? = null;

    @Column(nullable = true, updatable = false)
    @Lob
    var exception: String? = null
}