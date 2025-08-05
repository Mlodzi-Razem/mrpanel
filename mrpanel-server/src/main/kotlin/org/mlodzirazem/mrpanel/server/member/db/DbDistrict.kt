package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.mlodzirazem.mrpanel.server.db.BaseEntity

@Entity
@Table(name = "districts")
@SequenceGenerator(name = "seq_districts_id", sequenceName = "seq_districts_id", allocationSize = 50)
class DbDistrict : BaseEntity() {
    @GeneratedValue(generator = "seq_districts_id", strategy = GenerationType.SEQUENCE)
    override var id: Long? = null

    @Version
    @Column
    @ColumnDefault("0")
    var version: Long? = null

    @Column(nullable = false)
    var name: String? = null
    
    @Column(nullable = false, name = "group_email")
    var groupEmail: String? = null 
    
    @Column(nullable = false, name = "board_group_email")
    var boardGroupEmail: String? = null

    @OneToMany(mappedBy = "district", cascade = [CascadeType.ALL])
    var boardMembers: MutableSet<DbDistrictBoardMember>? = mutableSetOf()

}