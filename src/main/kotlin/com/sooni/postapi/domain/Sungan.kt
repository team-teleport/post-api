package com.sooni.postapi.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.sooni.postapi.dto.*
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Sungan(
    @Column(nullable = false)
    var title: String,

    var text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    var vehicle: Vehicle,

    var emoji: String?,
) {
    @ManyToOne
    var mainHashTag: MainHashTag? = null

    @ManyToMany
    @JoinTable(
        name = "sungan_detail_hashtags",
        joinColumns = [JoinColumn(name = "sungan_id")],
        inverseJoinColumns = [JoinColumn(name = "detailHashTag_id")]
    )
    var detailHashTags: MutableList<DetailHashTag> = ArrayList()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "sungan")
    var contents: MutableList<SunganContent> = ArrayList()

    @Column
    @ColumnDefault("0")
    var readCnt: Long = 0

    @Column
    @ColumnDefault("0")
    var likeCnt: Long = 0

    @OneToMany(mappedBy = "sungan")
    val comments: MutableList<Comment> = ArrayList()

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun convertToVo(): SunganVo =
        SunganVo(
            this.id!!,
            this.title,
            this.text,
            this.contents.map { content ->
                SunganContentVo(
                    content.type,
                    content.url
                )
            },
            this.emoji,
            this.mainHashTag,
            this.detailHashTags.map { dht -> dht.convertToVo() },
            UserVo(
                this.user.id!!,
                this.user.name,
                this.user.profileImage
            ),
            this.comments.map { comment ->
                CommentVo(
                    comment.id!!,
                    UserVo(
                        comment.user.id!!,
                        comment.user.name,
                        comment.user.profileImage
                    ),
                    comment.content,
                    comment.createdAt,
                    comment.updatedAt,
                    comment.likes.map { like ->
                        CommentLikeVo(
                            comment.id!!,
                            UserVo(
                                like.user.id!!,
                                like.user.name,
                                like.user.profileImage
                            ),
                            like.createdAt
                        )
                    }
                )
            },
            this.readCnt,
            this.likeCnt,
        )

    override fun toString() = kotlinToString(properties = toStringProperties)

    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Sungan::id)
        private val toStringProperties = arrayOf(
            Sungan::id,
            Sungan::vehicle,
            Sungan::title,
            Sungan::user,
            Sungan::createdAt
        )
    }
}
