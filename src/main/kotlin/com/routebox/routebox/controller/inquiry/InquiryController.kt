package com.routebox.routebox.controller.inquiry

import com.routebox.routebox.application.inquiry.CreateInquiryUseCase
import com.routebox.routebox.application.inquiry.GetInquiriesUseCase
import com.routebox.routebox.application.inquiry.GetInquiryUseCase
import com.routebox.routebox.controller.inquiry.dto.CreateInquiryRequest
import com.routebox.routebox.controller.inquiry.dto.CreateInquiryResponse
import com.routebox.routebox.controller.inquiry.dto.GetInquiryDetailResponse
import com.routebox.routebox.controller.inquiry.dto.GetInquiryResponse
import com.routebox.routebox.security.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "1:1 문의 관련 API")
@RequestMapping("/api")
@RestController
class InquiryController(
    private val createInquiryUseCase: CreateInquiryUseCase,
    private val getInquiriesUseCase: GetInquiriesUseCase,
    private val getInquiryUseCase: GetInquiryUseCase,
) {

    @Operation(
        summary = "문의 생성",
        description = "새로운 문의를 생성합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        value = ["/v1/inquiries"],
    )
    fun createInquiry(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @ModelAttribute request: CreateInquiryRequest,
    ): CreateInquiryResponse {
        val command = request.toCommand(userPrincipal.userId)
        val inquiryId = createInquiryUseCase(command)
        return CreateInquiryResponse(inquiryId)
    }

    @Operation(
        summary = "문의 목록 조회",
        description = "사용자의 모든 문의 목록을 조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/inquiries")
    fun getInquiries(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): GetInquiryResponse {
        val inquiries = getInquiriesUseCase(userPrincipal.userId)
        return GetInquiryResponse.from(inquiries)
    }

    @Operation(
        summary = "문의 상세 조회",
        description = "사용자의 문의를 상세조회합니다.",
        security = [SecurityRequirement(name = "access-token")],
    )
    @GetMapping("/v1/inquiries/{inquiryId}")
    fun getInquiryDetail(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable inquiryId: Long,
    ): GetInquiryDetailResponse {
        val inquiry = getInquiryUseCase(inquiryId)
        return GetInquiryDetailResponse.from(inquiry)
    }
}
