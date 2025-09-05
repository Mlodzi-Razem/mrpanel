package org.mlodzirazem.mrpanel.server.db.member

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.mlodzirazem.mrpanel.server.member.db.DbMember
import org.mlodzirazem.mrpanel.server.member.db.DbMemberAddress
import org.mlodzirazem.mrpanel.server.member.db.DbMemberContactDetails
import org.mlodzirazem.mrpanel.server.member.db.DbMemberTrace
import org.mlodzirazem.mrpanel.server.testing.EnableTestPostgresql
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.support.TransactionTemplate

@SpringBootTest
@EnableTestPostgresql
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DbMemberTraceTest(
    private val entityManager: EntityManager,
    private val transactionTemplate: TransactionTemplate
) : DescribeSpec({
    describe("members_trace") {
        val member = DbMember().apply {
            formalName = "formalName"
            preferredName = "preferredName"
            email = "email"
            contactDetails = DbMemberContactDetails().apply {
                privateEmail = "privateEmail"
                phoneNumber = "phoneNumber"
            }
            formalIdentifier = "formalIdentifier"
            address = DbMemberAddress().apply {
                countryCode = "POL"
                postalCode = "12345"
                city = "Warszawa"
                state = "Mazowieckie"
                province = "Warszawa"
                street = "ul. Sportowa"
                buildingNumber = "1"
            }
        }

        it("is added when member is added") {
            transactionTemplate.execute { entityManager.persist(member) }

            val memberTrace = entityManager.find(DbMemberTrace::class.java, member.id)

            assertSoftly {
                memberTrace.id shouldBe member.id
                memberTrace.preferredName shouldBe member.preferredName
                memberTrace.email shouldBe member.email
            }
        }

        it("is updated when member is updated") {
            transactionTemplate.execute {
                member.preferredName = "newPreferredName"
                member.email = "newEmail"
                entityManager.merge(member)
            }

            val memberTrace = entityManager.find(DbMemberTrace::class.java, member.id)

            assertSoftly {
                memberTrace.id shouldBe member.id
                memberTrace.preferredName shouldBe member.preferredName
                memberTrace.email shouldBe member.email
            }
        }
    }
})