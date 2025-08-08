package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.mlodzirazem.mrpanel.server.db.BaseEntity
import org.mlodzirazem.mrpanel.server.member.DistrictMembershipLevel

@Entity
@Table(name = "members_districts")
@SequenceGenerator(
    name =  BaseEntity.SEQUENCE_NAME,
    sequenceName = "seq_members_districts_id",
    allocationSize = 50
)
class DbMemberDistrict : BaseEntity() {
    @Version
    @Column
    @ColumnDefault("0")
    var version: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: DbMember? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    var district: DbDistrict? = null

    @Column(name = "membership_level", nullable = false, length = 31)
    @Enumerated(EnumType.STRING)
    var membershipLevel: DistrictMembershipLevel? = null

}