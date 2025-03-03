package com.routebox.routebox.domain.comment

import com.routebox.routebox.application.comment.dto.GetAllCommentsOfPostDto
import com.routebox.routebox.domain.route.Route
import com.routebox.routebox.domain.user.User
import com.routebox.routebox.exception.comment.CommentDeleteForbiddenException
import com.routebox.routebox.exception.comment.CommentEditForbiddenException
import com.routebox.routebox.exception.comment.CommentNotFoundException
import com.routebox.routebox.exception.route.RouteNotFoundException
import com.routebox.routebox.exception.user.UserNotFoundException
import com.routebox.routebox.infrastructure.comment.CommentRepository
import com.routebox.routebox.infrastructure.route.RouteRepository
import com.routebox.routebox.infrastructure.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val routeRepository: RouteRepository,
) {
    /*댓글 작성*/
    @Transactional
    fun writeComment(userId: Long, routeId: Long, content: String) {
        // id에 해당하는 각 객체 조회
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

    /* 게시글의 모든 댓글 조회 */
    @Transactional(readOnly = true)
    fun getAllCommentsOfPost(routeId: Long): List<GetAllCommentsOfPostDto> {
        val comments = commentRepository.findByRoute_Id(routeId)

        return comments.map { comment ->
            GetAllCommentsOfPostDto(
                commentId = comment.id,
                userNickname = comment.user.nickname,
                userProfileImageUrl = comment.user.profileImageUrl,
                content = comment.content,
                timeAgo = getTimeAgo(comment.createdAt),
            )
        }
    }

    /*오늘을 기준으로 댓글 작성시간을 상대시간으로 변환*/
    fun getTimeAgo(createdAt: LocalDateTime): String {
        val now = LocalDateTime.now()
        // now와 createdAt의 날짜(년,월,일) 단위 차이를 구한다
        val period = Period.between(createdAt.toLocalDate(), now.toLocalDate())
        // now와 createdAt의 시간 차이를 구한다
        val duration = Duration.between(createdAt, now)

        return when {
            period.years > 0 -> "${period.years}년 전"
            period.months > 0 -> "${period.months}개월 전"
            period.days > 0 -> "${period.days}일 전"
            duration.toHours() > 0 -> "${duration.toHours()}시간 전"
            duration.toMinutes() > 0 -> "${duration.toMinutes()}분 전"
            else -> "방금 전"
        }
    }

    /*댓글 내용 수정*/
    @Transactional
    fun modifyComment(id: Long, content: String, userId: Long) {
        // 수정할 댓글을 조회
        val comment: Comment = commentRepository.findById(id)
            .orElseThrow { throw CommentNotFoundException() }

        // 요청자 조회
        val user: User = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException() }
        // 요청자와 댓글 작성자가 다를 경우 예외 발생
        if (comment.user.id != user.id) {
            throw CommentEditForbiddenException()
        }

        // 댓글 내용 수정
        comment.content = content
    }

    /*댓글 삭제*/
    @Transactional
    fun deleteComment(id: Long, userId: Long) {
        // 삭제할 댓글을 조회
        val comment: Comment = commentRepository.findById(id)
            .orElseThrow { throw CommentNotFoundException() }

        // 요청자 조회
        val user: User = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException() }
        // 요청자와 댓글 작성자가 다를 경우 예외 발생
        if (comment.user.id != user.id) {
            throw CommentDeleteForbiddenException()
        }

        // 댓글 삭제
        commentRepository.delete(comment)
    }
}
