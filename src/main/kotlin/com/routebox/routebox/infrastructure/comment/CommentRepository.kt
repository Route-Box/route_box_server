package com.routebox.routebox.infrastructure.comment

import com.routebox.routebox.domain.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

@Suppress("ktlint:standard:function-naming")
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByRoute_Id(routeId: Long): List<Comment>
}
