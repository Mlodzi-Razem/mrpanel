package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.mlodzirazem.mrpanel.server.db.BaseEntity

@Entity
@Table(name = "members_trace")
class DbMemberTrace : BaseEntity() {
    @Column(name = "preferred_name")
    var preferredName: String? = null

    @Column(name = "email")
    var email: String? = null
}