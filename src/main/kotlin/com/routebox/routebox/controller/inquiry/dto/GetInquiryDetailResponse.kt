package com.routebox.routebox.controller.inquiry.dto

import InquiryType
import com.routebox.routebox.application.inquiry.dto.GetInquiryResult
import com.routebox.routebox.domain.inquiry.InquiryStatus

data class GetInquiryDetailResponse(
    val inquiryId: Long,
    val userId: Long,
    val type: InquiryType,
    val content: String,
    val status: InquiryStatus,
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
