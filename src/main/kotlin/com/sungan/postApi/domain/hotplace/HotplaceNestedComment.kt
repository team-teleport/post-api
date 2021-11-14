package com.sungan.postApi.domain.hotplace

import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.HotplaceNestedCommentVo
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE table_product SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
class HotplaceNestedComment(
    @ManyToOne
    @JoinColumn(name = "hotplace_comment_id")
    var hotplaceComment: HotplaceComment,
    @Column(nullable = false)
    var content: String,
    @Embedded
    var userInfo: UserInfo,
) : PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun convertToVo() = HotplaceNestedCommentVo(id!!, hotplaceComment.id!!, content, userInfo, createdAt, updatedAt)
}