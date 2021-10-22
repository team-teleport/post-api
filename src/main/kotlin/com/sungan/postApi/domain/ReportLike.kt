package com.sungan.postApi.domain

import javax.persistence.*

@Entity
class ReportLike(
    @ManyToOne
    @JoinColumn(name = "report_id")
    var report: Report,
    @Column
    var userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}