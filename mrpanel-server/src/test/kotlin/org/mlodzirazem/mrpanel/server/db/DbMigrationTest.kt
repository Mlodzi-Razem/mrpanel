package org.mlodzirazem.mrpanel.server.db

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.assertDoesNotThrow
import org.mlodzirazem.mrpanel.server.testing.PostgresJpaTest
import org.mlodzirazem.mrpanel.server.testing.TestPostgresController.restoreSchema
import org.springframework.transaction.support.TransactionTemplate

@PostgresJpaTest
class DbMigrationTest(
    private val transactionTemplate: TransactionTemplate,
    private val entityManager: EntityManager
) : DescribeSpec({
    beforeSpec {
        restoreSchema()
    }

    describe("context") {
        it("starts with empty database") {
            entityManager.createNativeQuery("SELECT COUNT(*) FROM members_trace").singleResult shouldBeEqual 0L
        }

        it("saves members_trace") {
            assertDoesNotThrow {
                transactionTemplate.executeWithoutResult {
                    entityManager.createNativeQuery("INSERT INTO members_trace (id, preferred_name, email) VALUES (1, 'name', 'email')")
                        .executeUpdate()
                }
            }
        }

        it("preserves members_trace between test methods") {
            entityManager.createNativeQuery("SELECT COUNT(*) FROM members_trace").singleResult shouldBeEqual 1L
        }
    }
})

@PostgresJpaTest
class DbSecondContextTest(private val entityManager: EntityManager) : DescribeSpec({
    beforeSpec {
        restoreSchema()
    }

    describe("context") {
        it("starts with empty database") {
            entityManager.createNativeQuery("SELECT COUNT(*) FROM members_trace").singleResult shouldBeEqual 0L
        }
    }
})