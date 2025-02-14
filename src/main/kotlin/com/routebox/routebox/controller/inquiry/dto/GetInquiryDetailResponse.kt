package com.routebox.routebox.controller.inquiry.dto

import com.routebox.routebox.application.inquiry.dto.GetInquiryResult

data class GetInquiryDetailResponse(
    val inquiryId: Long,
    val userId: Long,
    val type: String,
    val content: String,
    val status: String,
    val reply: String?,
    val imageUrls: List<String>?,
) {
    companion object {
        fun from(inquiry: GetInquiryResult): GetInquiryDetailResponse {
            return GetInquiryDetailResponse(
                inquiryId = inquiry.inquiryId,
                userId = inquiry.userId,
                type = inquiry.type,
                content = inquiry.content,
                status = inquiry.status,
                reply = inquiry.reply,
                imageUrls = inquiry.imageUrls.ifEmpty { null },
            )
        }
    }
}
