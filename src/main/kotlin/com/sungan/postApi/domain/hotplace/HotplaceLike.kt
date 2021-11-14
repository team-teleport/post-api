package com.sungan.postApi.domain.hotplace

import javax.persistence.*

@Entity
class HotplaceLike(
    userId: Long,
    hotplace: Hotplace
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    val userId: Long = userId

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "hotplace_id")
    var hotplace: Hotplace = hotplace
}