package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.mlodzirazem.mrpanel.server.db.BaseEntity
import org.mlodzirazem.mrpanel.server.member.MemberDistrictRole

@Entity
@Table(name = "district_board_members")
@SequenceGenerator(
    name =  BaseEntity.SEQUENCE_NAME,
    sequenceName = "seq_district_board_members_id",
    allocationSize = 50
)
class DbDistrictBoardMember : BaseEntity() {

    @Version
    @Column
    @ColumnDefault("0")
    var version: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    var district: DbDistrict? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: DbMember? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 31)
    var role: MemberDistrictRole? = null

}