package com.sungan.postApi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.dto.CommentLikeVo
import com.sungan.postApi.dto.CommentVo
import com.sungan.postApi.dto.NestedCommentVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Comment(
    @Column(nullable = false)
    var content: String,

    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sungan_id")
    val sungan: Sungan
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "comment")
    var likes: MutableList<CommentLike> = ArrayList()

    @OneToMany(mappedBy = "comment")
    var nestedComments: MutableList<NestedComment> = ArrayList()

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Comment::id)
        private val toStringProperties = arrayOf(
            Comment::id,
            Comment::content,
            Comment::userId,
            Comment::createdAt,
            Comment::updatedAt
        )
    }

    fun convertToVo(): CommentVo = CommentVo(
        this.id!!,
        this.userId,
        this.content,
        this.createdAt,
        this.updatedAt,
        this.likes.size.toLong(),
        this.nestedComments.asSequence().map { nestedComment -> nestedComment.convertToVo() }.toList()
    )
}