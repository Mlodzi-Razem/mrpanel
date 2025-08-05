package org.mlodzirazem.mrpanel.server.audit

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.lang.reflect.Method

/**
 * Intercepts methods annotated with `@AuditedOperation` to log audit information.
 *
 * @constructor
 * @param auditAspectLogger Handles persistence and processing of audit logs.
 * @param auditContextCreator Creates audit context providing details about the operation and its context.
 */
@Aspect
@Component
class AuditAspect(
    private val auditAspectLogger: AuditAspectLogger,
    private val auditContextCreator: AuditContextCreator
) {
    private fun getAuditedOperationId(joinPoint: JoinPoint): String {
        val signature = joinPoint.signature as MethodSignature
        val method: Method = signature.method
        return method.getAnnotation(AuditedOperation::class.java)?.id
            ?: throw IllegalStateException("No AuditedOperation ID found")
    }


    @Pointcut("@annotation(org.mlodzirazem.mrpanel.server.audit.AuditedOperation)")
    fun auditedOperations() {
    }

    @AfterReturning("auditedOperations()")
    fun onSuccess(joinPoint: JoinPoint) {
        val operationId = getAuditedOperationId(joinPoint)
        val context = auditContextCreator.createContext(operationId, exception = null)
        auditAspectLogger.logOperation(context)
    }

    @AfterThrowing("auditedOperations()", throwing = "exception")
    fun onFailure(joinPoint: JoinPoint, exception: Throwable) {
        val operationId = getAuditedOperationId(joinPoint)
        val context = auditContextCreator.createContext(operationId, exception)
        auditAspectLogger.logOperation(context)
    }

}