package com.sungan.postApi.domain

import javax.persistence.*

@Entity
class MainHashTag (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false, unique = true)
    val name: String
        )