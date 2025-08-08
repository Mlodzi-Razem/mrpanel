package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.mlodzirazem.mrpanel.server.db.BaseEntity

@Entity
@Table(name = "members")
@SequenceGenerator(name = BaseEntity.SEQUENCE_NAME, sequenceName = "seq_members_id", allocationSize = 1)
class DbMember : BaseEntity() {
    @Version
    @Column
    @ColumnDefault("0")
    var version: Long? = null

    @Column(nullable = false, name = "preferred_name")
    var preferredName: String? = null

    @Column(nullable = false, name = "formal_name")
    var formalName: String? = null

    @Column(nullable = false)
    var email: String? = null

    @Column(nullable = false, name = "formal_identifier")
    var formalIdentifier: String? = null

    @Embedded
    var address: DbMemberAddress? = null

    @Embedded
    var contactDetails: DbMemberContactDetails? = null

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
    var districts: MutableSet<DbMemberDistrict>? = mutableSetOf()
}