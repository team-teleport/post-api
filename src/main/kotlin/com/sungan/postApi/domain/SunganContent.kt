package com.sungan.postApi.domain

import javax.persistence.*

@Entity
class SunganContent (
    var url: String,
    var type: ContentType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sungan_id", nullable = true)
    var sungan: Sungan
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    enum class ContentType {
        IMAGE,
        VIDEO,
        EMOJI
    }
}