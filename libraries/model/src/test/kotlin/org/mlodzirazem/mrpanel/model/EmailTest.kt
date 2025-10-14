package org.mlodzirazem.mrpanel.model

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.email
import io.kotest.property.arbitrary.of
import io.kotest.property.forAll

class EmailTest : DescribeSpec({
    describe("constructor") {
        it("throws exception when email is blank") {
            forAll(Arb.of("", " ", "  ")) { blankString ->
                shouldThrow<IllegalArgumentException> { Email(blankString) }
                true
            }
        }

        it("throws exception when email is not valid") {
            forAll(Arb.of("abc", "test@domain", "test!domain.com")) { invalidEmail ->
                shouldThrow<IllegalArgumentException> { Email(invalidEmail) }
                true
            }
        }

        it("does not throw when email is valid") {
            forAll(Arb.email()) { validEmail ->
                shouldNotThrowAny { Email(validEmail) }
                true
            }
        }
    }

    describe("username") {
        it("returns username") {
            forAll(Arb.email()) { email ->
                Email(email).username == email.substringBefore("@")
            }
        }
    }

    describe("domain") {
        it("returns domain") {
            forAll(Arb.email()) { email ->
                Email(email).domain == email.substringAfter("@")
            }
        }
    }

    describe("toString") {
        it("returns value") {
            forAll(Arb.email()) { email ->
                Email(email).toString() == email
            }
        }
    }
})