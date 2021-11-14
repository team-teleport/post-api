package com.sungan.postApi.domain.sungan

import com.sungan.postApi.domain.sungan.Sungan
import javax.persistence.*

@Entity
class UserViewedSungan(
    @ManyToOne
    val sungan: Sungan,
    val userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}