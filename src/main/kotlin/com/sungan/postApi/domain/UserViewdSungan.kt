package com.sungan.postApi.domain

import javax.persistence.*

@Entity
class UserViewdSungan(
    @ManyToOne
    val sungan: Sungan,
    @Column
    val user_id: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}