package com.sungan.postApi.domain

import javax.persistence.*

@Entity
class UserViewdSungan(
    @ManyToOne
    val sungan: Sungan,
    val userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}