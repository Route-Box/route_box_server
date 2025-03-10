package com.routebox.routebox.infrastructure.comment

import com.routebox.routebox.domain.comment.Comment
import com.routebox.routebox.domain.comment.constant.CommentStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

@Suppress("ktlint:standard:function-naming")
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByRoute_IdAndStatus(routeId: Long, status: CommentStatus): List<Comment>
    fun findByIdAndStatus(routeId: Long, status: CommentStatus): Optional<Comment>
}
