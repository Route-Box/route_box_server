package com.routebox.routebox.domain.comment

import com.routebox.routebox.domain.route.Route
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.exception.route.RouteNotFoundException
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.infrastructure.comment.CommentRepository
import com.routebox.routebox.infrastructure.route.RouteRepository
import com.routebox.routebox.infrastructure.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val routeRepository: RouteRepository,
) {
    @Transactional
    fun writeComment(userId: Long, routeId: Long, content: String) {
        // 댓글을 작성하려면 User와 Route가 존재해야 하므로 ID로 조회
        val user: User = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException() }
        val route: Route = routeRepository.findById(routeId)
            .orElseThrow { throw RouteNotFoundException() }

        // 조회한 User와 Route를 기반으로 새로운 댓글 생성
        val comment = Comment(
            route = route,
            user = user,
            content = content,
        )
        commentRepository.save(comment)
    }
}
