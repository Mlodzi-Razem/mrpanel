package org.mlodzirazem.mrpanel.server.audit

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.stereotype.Component

private const val SUCCESSFUL_OPERATION_ID = "SUCCESSFUL_OPERATION_ID"
private const val FAILED_OPERATION_ID = "FAILED_OPERATION_ID"

@ApplyExtension(SpringExtension::class)
@SpringBootTest(classes = [AuditAspect::class, AuditAspectTest.AuditedTestClass::class])
@EnableAspectJAutoProxy
class AuditAspectTest(
    @MockkBean
    private val auditAspectLogger: AuditAspectLogger,

    @MockkBean
    private val auditContextCreator: AuditContextCreator,

    private val auditedTestClass: AuditedTestClass
) : DescribeSpec({
    describe("onSuccess") {
        clearAllMocks()

        val operationContextSlot = slot<AuditContext>()

        every { auditContextCreator.createContext(SUCCESSFUL_OPERATION_ID, null) } returns AuditContext(
            memberId = 1L,
            operationId = SUCCESSFUL_OPERATION_ID,
            payload = null,
            url = null,
            exception = null
        )
        justRun { auditAspectLogger.logOperation(capture(operationContextSlot)) }

        auditedTestClass.successfulOperation()

        it("should log an operation") {
            verify(exactly = 1) { auditAspectLogger.logOperation(any()) }
        }
        it("should log the correct operation ID") {
            operationContextSlot.captured.operationId shouldBe SUCCESSFUL_OPERATION_ID
        }
    }

    describe("onFailure") {
        clearAllMocks()

        val operationContextSlot = slot<AuditContext>()
        val exceptionSlot = slot<Throwable>()

        every { auditContextCreator.createContext(FAILED_OPERATION_ID, capture(exceptionSlot)) } answers {
            AuditContext(
                memberId = 1L,
                operationId = FAILED_OPERATION_ID,
                payload = null,
                url = null,
                exception = exceptionSlot.captured
            )
        }
        justRun { auditAspectLogger.logOperation(capture(operationContextSlot)) }

        assertThrows<Exception> {
            auditedTestClass.failedOperation()
        }

        it("should log an operation") {
            verify(exactly = 1) { auditAspectLogger.logOperation(any()) }
        }
        it("should log the correct operation ID") {
            operationContextSlot.captured.operationId shouldBe FAILED_OPERATION_ID
        }
        it("should log the correct exception") {
            operationContextSlot.captured.exception!!.stackTraceToString() shouldBe exceptionSlot.captured.stackTraceToString()
        }
    }
}) {

    @Component
    class AuditedTestClass {
        @AuditedOperation(id = SUCCESSFUL_OPERATION_ID)
        fun successfulOperation() {
        }

        @AuditedOperation(id = FAILED_OPERATION_ID)
        fun failedOperation() {
            throw Exception("Failed operation")
        }

    }
}
