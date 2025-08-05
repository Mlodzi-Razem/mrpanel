package org.mlodzirazem.mrpanel.server.db

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.mlodzirazem.mrpanel.server.testing.EnableTestPostgresql
import org.mlodzirazem.mrpanel.server.testing.TestPostgresController.restoreSchema
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.support.TransactionTemplate

@SpringBootTest
@EnableTestPostgresql
class DbMigrationTest(
    private val transactionTemplate: TransactionTemplate,
    @PersistenceContext private val entityManager: EntityManager
) : DescribeSpec({
    beforeSpec {
        restoreSchema()
    }

    describe("context") {
        it("starts with empty database") {
            entityManager.createNativeQuery("SELECT COUNT(*) FROM members_trace").singleResult shouldBe 0
        }
        it("saves members_trace") {
            transactionTemplate.execute {
                entityManager.createNativeQuery("INSERT INTO members_trace (id, preferred_name, email) VALUES (1, 'name', 'email')")
                    .executeUpdate()
            }
        }
        it("preserves members_trace between test methods") {
            entityManager.createNativeQuery("SELECT COUNT(*) FROM members_trace").singleResult shouldBe 1
        }
    }
})

@SpringBootTest
@EnableTestPostgresql
class DbSecondContextTest(@PersistenceContext private val entityManager: EntityManager) : DescribeSpec({
    beforeSpec {
        restoreSchema()
    }

    describe("context") {
        it("starts with empty database") {
            entityManager.createNativeQuery("SELECT COUNT(*) FROM members_trace").singleResult shouldBe 0
        }
    }
})