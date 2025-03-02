package com.routebox.routebox.controller.comment

import com.routebox.routebox.application.comment.DeleteCommentUseCase
import com.routebox.routebox.application.comment.GetAllCommentsOfPostUseCase
import com.routebox.routebox.application.comment.ModifyCommentUseCase
import com.routebox.routebox.application.comment.WriteCommentUseCase
import com.routebox.routebox.controller.comment.dto.GetAllCommentsOfPostResponse
import com.routebox.routebox.controller.comment.dto.PatchModifyCommentRequest
import com.routebox.routebox.controller.comment.dto.PostWriteCommentRequest
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
    private val modifyCommentUseCase: ModifyCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
) {
    @Operation(
        summary = "댓글 작성",
        description = "<p>게시글에 댓글을 작성합니다." +
            "<p>댓글 내용에 <code>null</code>이나 빈 값이 들어갈 경우, 작성되지 않습니다.",
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

    @Operation(
        summary = "댓글 내용 수정",
        description = "댓글 내용을 수정합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PatchMapping("/{commentId}")
    fun modifyComment(
        @PathVariable commentId: Long,
        @RequestBody @Valid request: PatchModifyCommentRequest,
    ): ResponseEntity<String> {
        modifyCommentUseCase(commentId, request.content)

        return ResponseEntity.ok("댓글 수정을 완료했습니다.")
    }

    @Operation(
        summary = "댓글 삭제",
        description = "댓글을 삭제합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): ResponseEntity<String> {
        deleteCommentUseCase(commentId)

        return ResponseEntity.ok("댓글을 삭제했습니다.")
    }
}
