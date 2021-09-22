package com.sooni.postapi.domain

import com.sooni.postapi.dto.DetailHashTagVo
import javax.persistence.*

@Entity
class DetailHashTag(
    @Column(unique = true, nullable = false)
    val name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToMany(mappedBy = "detailHashTags")
    val sungan: MutableList<Sungan> = ArrayList()

    fun convertToVo() = DetailHashTagVo(this.id!!, this.name)
}