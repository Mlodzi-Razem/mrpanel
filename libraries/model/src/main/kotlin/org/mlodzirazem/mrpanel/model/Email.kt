package org.mlodzirazem.mrpanel.model

import org.apache.commons.validator.routines.EmailValidator

@JvmInline
value class Email(val value: String) {
    init {
        require(value.isNotBlank()) { "Email cannot be blank" }
        require(EmailValidator.getInstance().isValid(value)) { "Email is not valid" }
    }

    val username get() = value.substringBeforeLast("@")
    val domain get() = value.substringAfterLast("@")

    override fun toString(): String {
        return value
    }
}