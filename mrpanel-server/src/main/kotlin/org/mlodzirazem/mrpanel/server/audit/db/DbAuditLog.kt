package org.mlodzirazem.mrpanel.server.audit.db

import jakarta.persistence.*
import org.mlodzirazem.mrpanel.server.db.BaseEntity

@Entity
@Table(name = "audit_log")
@SequenceGenerator(name = "seq_audit_log_id", sequenceName = "seq_audit_log_id", allocationSize = 50, initialValue = 0)
class DbAuditLog : BaseEntity() {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_audit_log_id")
    override var id: Long? = null

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