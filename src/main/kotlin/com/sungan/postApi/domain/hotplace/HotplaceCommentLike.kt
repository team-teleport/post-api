package com.sungan.postApi.domain.hotplace

import javax.persistence.*

@Entity
class HotplaceCommentLike(
    @Column(nullable = false)
    var userId: Long,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "hotplace_comment_id")
    var hotplaceComment: HotplaceComment
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}