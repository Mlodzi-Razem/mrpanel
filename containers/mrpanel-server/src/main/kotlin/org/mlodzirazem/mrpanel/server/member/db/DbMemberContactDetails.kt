package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class DbMemberContactDetails {
    @Column(nullable = false, name = "private_email", length = 511)
    var privateEmail: String? = null

    @Column(nullable = false, name = "phone_number", length = 127)
    var phoneNumber: String? = null

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
    var socialMedia: MutableSet<DbMemberSocialMedia>? = mutableSetOf()
}