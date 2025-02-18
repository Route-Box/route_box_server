package com.routebox.routebox.controller.inquiry.dto

import com.routebox.routebox.application.inquiry.dto.GetInquiryResult

data class InquiryDto(
    val inquiryId: Long,
    val content: String,
    val status: String,
) {
    companion object {
        fun from(inquiry: GetInquiryResult): InquiryDto {
            return InquiryDto(
                inquiryId = inquiry.inquiryId,
                content = inquiry.content,
                status = inquiry.status,
            )
        }
    }
}
