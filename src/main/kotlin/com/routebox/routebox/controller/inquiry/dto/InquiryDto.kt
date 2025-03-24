package com.routebox.routebox.controller.inquiry.dto

import com.routebox.routebox.application.inquiry.dto.GetInquiryResult
import com.routebox.routebox.domain.inquiry.InquiryStatus

data class InquiryDto(
    val inquiryId: Long,
    val content: String,
    val status: InquiryStatus,
    val createdAt: String,
) {
    companion object {
        fun from(inquiry: GetInquiryResult): InquiryDto {
            return InquiryDto(
                inquiryId = inquiry.inquiryId,
                content = inquiry.content,
                status = inquiry.status,
                createdAt = inquiry.createdAt,
            )
        }
    }
}
