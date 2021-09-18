package com.sooni.postapi.domain

import javax.persistence.*

@Entity
class DetailHashTag (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(unique = true, nullable = false)
    val name: String
        )