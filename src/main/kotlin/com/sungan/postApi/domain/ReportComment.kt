package com.sungan.postApi.domain

import javax.persistence.*

@Entity
class ReportComment(
    content: String,
    report: Report
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var content: String = content

    @ManyToOne
    @JoinColumn(name = "report_id")
    val report: Report = report
}