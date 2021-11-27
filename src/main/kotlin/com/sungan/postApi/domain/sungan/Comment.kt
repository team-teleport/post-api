package com.sungan.postApi.domain.sungan

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sungan.postApi.domain.PostBaseEntity
import com.sungan.postApi.domain.UserInfo
import com.sungan.postApi.dto.BestCommentVo
import com.sungan.postApi.dto.CommentVo
import com.sungan.postApi.dto.SunganBestCommentVo
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Table(
    indexes = [
        Index(name = "comment_sungan_index", columnList = "sungan_id, deleted")
    ]
)
class Comment(
    @Column(nullable = false)
    var content: String,

    @Embedded
    var userInfo: UserInfo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sungan_id")
    val sungan: Sungan
): PostBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.REMOVE])
    var likes: MutableList<CommentLike> = ArrayList()

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.REMOVE])
    @OrderBy(value = "created_at DESC")
    var nestedComments: MutableList<NestedComment> = ArrayList()

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Comment::id)
        private val toStringProperties = arrayOf(
            Comment::id,
            Comment::content,
            Comment::userInfo,
            Comment::createdAt,
            Comment::updatedAt
        )
    }

    fun convertToVo(): CommentVo = CommentVo(
        this.id!!,
        this.userInfo,
        this.content,
        this.createdAt,
        this.updatedAt,
        this.likes.size.toLong(),
        this.nestedComments.asSequence().map { nestedComment -> nestedComment.convertToVo() }.toList()
    )

    fun convertToBestComment() = SunganBestCommentVo(id!!, content, userInfo, createdAt, updatedAt, sungan.id!!)
}