package com.sooni.postapi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sooni.postapi.dto.CommentLikeVo
import com.sooni.postapi.dto.CommentVo
import com.sooni.postapi.dto.UserVo
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Comment(
    @Column(nullable = false)
    var content: String,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sungan_id")
    val sungan: Sungan
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "comment")
    var likes: MutableList<CommentLike> = ArrayList()

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
            Comment::user,
            Comment::createdAt
        )
    }

    fun convertToVo(): CommentVo = CommentVo(
        this.id!!,
        UserVo(
            this.user.id!!,
            this.user.name,
            this.user.profileImage
        ),
        this.content,
        this.createdAt,
        this.updatedAt,
        this.likes.map { like ->
            CommentLikeVo(
                like.id!!,
                UserVo(like.user.id!!, like.user.name, like.user.profileImage),
                like.createdAt
            )
        }
    )
}