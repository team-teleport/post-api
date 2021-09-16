package com.sooni.postapi.domain

import javax.persistence.*

@Entity
class SunganContent (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var url: String,
    var type: ContentType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sungan_id", nullable = true)
    var sungan: Sungan
) {
    enum class ContentType {
        IMAGE,
        VIDEO,
        EMOJI
    }
}