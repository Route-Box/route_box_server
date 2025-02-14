package com.routebox.routebox.controller.inquiry.dto

import InquiryType
import com.routebox.routebox.application.inquiry.dto.CreateInquiryCommand
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.Length
import org.springframework.web.multipart.MultipartFile

data class CreateInquiryRequest(
    @Schema(description = "문의 내용", example = "앱에서 오류가 발생했습니다.")
    @field:Length(max = 400)
    val content: String,

    @Schema(description = "문의 유형", example = "ERROR")
    val inquiryType: InquiryType,

    @Schema(description = "문의 관련 이미지들")
    val images: List<MultipartFile>?,
) {
    fun toCommand(userId: Long) = CreateInquiryCommand(
        userId = userId,
        content = content,
        inquiryType = inquiryType,
        images = images,
    )
}
