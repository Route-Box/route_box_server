package com.routebox.routebox.controller.comment

import com.routebox.routebox.application.comment.GetAllCommentsOfPostUseCase
import com.routebox.routebox.application.comment.WriteCommentUseCase
import com.routebox.routebox.controller.comment.dto.GetAllCommentsOfPostResponse
import com.routebox.routebox.controller.comment.dto.PostWriteCommentRequest
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "댓글 API")
@RequestMapping("/api/v1/comments")
@RestController
@Validated
class CommentController(
    private val writeCommentUseCase: WriteCommentUseCase,
    private val getAllCommentsOfPostUseCase: GetAllCommentsOfPostUseCase,
) {
    @Operation(
        summary = "댓글 작성",
        description = "게시글에 댓글을 작성합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping("/{routeId}")
    fun writeComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable routeId: Long,
        @RequestBody request: PostWriteCommentRequest,
    ): ResponseEntity<String> {
        // 댓글을 작성한다
        writeCommentUseCase(
            userId = userPrincipal.userId,
            routeId = routeId,
            content = request.content,
        )

        return ResponseEntity.ok("댓글 작성이 완료되었습니다.")
    }

    @Operation(
        summary = "게시글의 모든 댓글 조회",
        description = "게시글의 모든 댓글을 조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/{routeId}")
    fun getAllComments(@PathVariable routeId: Long): ResponseEntity<GetAllCommentsOfPostResponse> {
        // 게시글의 모든 댓글을 조회한다
        val comments = getAllCommentsOfPostUseCase(routeId)

        // response 객체로 변환한다
        val response = GetAllCommentsOfPostResponse(comments)
        return ResponseEntity.ok(response)
    }
}
