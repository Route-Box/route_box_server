package com.routebox.routebox.domain.comment_report

import com.routebox.routebox.domain.comment.Comment
import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "comment_report")
@Entity
class CommentReport(
    reporter: User,
    reportedComment: Comment,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_report_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    val reporter: User = reporter

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_comment_id")
    val reportedComment: Comment = reportedComment
}
