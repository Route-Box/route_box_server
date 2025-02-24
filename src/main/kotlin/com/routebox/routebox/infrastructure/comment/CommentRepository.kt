package com.routebox.routebox.infrastructure.comment

import com.routebox.routebox.domain.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByRouteId(routeId: Long): List<Comment>
}
