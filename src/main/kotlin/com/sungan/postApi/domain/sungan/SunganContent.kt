package com.sungan.postApi.domain.sungan

import com.sungan.postApi.dto.SunganContentVo
import javax.persistence.*

@Entity
class SunganContent (
    var url: String,
    var type: ContentType,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name="sungan_id", nullable = true)
    var sungan: Sungan
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun convertToVo() = SunganContentVo(
        type,
        url
    )

    enum class ContentType {
        IMAGE,
        VIDEO,
        EMOJI
    }
}