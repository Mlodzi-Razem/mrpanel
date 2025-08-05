package org.mlodzirazem.mrpanel.server.audit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuditedOperation(
    val id: String
)
