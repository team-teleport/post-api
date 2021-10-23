package com.sungan.postApi.domain

import com.sungan.postApi.dto.HotplaceNestedCommentVo
import javax.persistence.*

@Entity
class HotplaceNestedComment(
    @ManyToOne
    @JoinColumn(name = "hotplace_comment_id")
    var hotplaceComment: HotplaceComment,
    @Column(nullable = false)
    var content: String,
    val userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun convertToVo() = HotplaceNestedCommentVo(id!!, hotplaceComment.id!!, content, userId)
}