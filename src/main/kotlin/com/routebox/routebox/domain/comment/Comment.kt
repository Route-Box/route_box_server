package com.routebox.routebox.domain.comment

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import com.routebox.routebox.domain.route.Route
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

@Table(name = "comments")
@Entity
class Comment(
    route: Route,
    user: User,
    content: String,
) : TimeTrackedBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    val route: Route = route

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User = user

    @Column(nullable = false, length = 500)
    var content: String = content.take(500)
}
