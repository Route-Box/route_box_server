package com.routebox.routebox.controller.comment

import com.routebox.routebox.application.comment.WriteCommentUseCase
import com.routebox.routebox.controller.comment.dto.PostWriteCommentRequest
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
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
) {
    /*댓글 작성*/
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
    ): ResponseEntity<Unit> {
        writeCommentUseCase(
            userId = userPrincipal.userId,
            routeId = routeId,
            content = request.content,
        )
        return ResponseEntity.noContent().build()
    }

//    @Operation(
//    summary = "댓글 리스트 조회",
//    description = "게시글의 모든 댓글을 조회합니다.",
//    security = [SecurityRequirement(name = "access-token")],
//    )
//    @GetMapping("/{routeId}")
//    fun findAvailableCoupons(
//        @AuthenticationPrincipal userPrincipal: UserPrincipal,
//        @PathVariable routeId: Long,
//    ): GetCommentListResponse {
//        getCommentListUseCase(userId = userPrincipal.userId)
//    }
}
