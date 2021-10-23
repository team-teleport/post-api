package com.sungan.postApi.domain

import javax.persistence.*

@Entity
class HotplaceCommentLike(
    @Column(nullable = false)
    var userId: Long,
    @ManyToOne
    @JoinColumn(name = "hotplace_comment_id")
    var hotplaceComment: HotplaceComment
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}