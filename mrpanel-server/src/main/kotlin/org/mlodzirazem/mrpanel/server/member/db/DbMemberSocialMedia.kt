package org.mlodzirazem.mrpanel.server.member.db

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.mlodzirazem.mrpanel.server.db.BaseEntity

@Entity
@Table(name = "member_social_media")
@SequenceGenerator(
    name =  BaseEntity.SEQUENCE_NAME,
    sequenceName = "seq_member_social_media_id",
    allocationSize = 50
)
class DbMemberSocialMedia : BaseEntity() {
    @Version
    @Column
    @ColumnDefault("0")
    var version: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: DbMember? = null

    @Column(name = "media_name", nullable = false, length = 512)
    var mediaName: String? = null

    @Column(nullable = false, length = 2048)
    var url: String? = null
}