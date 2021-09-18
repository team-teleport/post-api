package com.sooni.postapi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import javax.persistence.*

@Entity
class CommentLike (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    val comment: Comment
        ) {
        override fun toString() = kotlinToString(properties = CommentLike.toStringProperties)

        override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

        override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

        companion object {
                private val equalsAndHashCodeProperties = arrayOf(CommentLike::id)
                private val toStringProperties = arrayOf(
                        CommentLike::id,
                        CommentLike::user,
                        CommentLike::comment
                )
        }
}