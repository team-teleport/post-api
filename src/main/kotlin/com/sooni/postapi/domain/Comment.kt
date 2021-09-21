package com.sooni.postapi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Comment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false)
    val content: String,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sungan_id")
    val sungan: Sungan
        ) {
    @OneToMany(mappedBy = "comment")
    val likes: MutableList<CommentLike> = ArrayList()

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now()
    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Comment::id)
        private val toStringProperties = arrayOf(
            Comment::id,
            Comment::content,
            Comment::user,
            Comment::createdAt
        )
    }
}