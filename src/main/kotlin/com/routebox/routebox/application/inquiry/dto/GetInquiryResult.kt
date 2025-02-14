package com.routebox.routebox.application.inquiry.dto

import com.routebox.routebox.domain.inquiry.Inquiry

data class GetInquiryResult(
    val inquiryId: Long,
    val userId: Long,
    val type: String,
    val content: String,
    val status: String,
    val reply: String?,
    val imageUrls: List<String>,
) {
    companion object {
        fun from(inquiry: Inquiry, imageUrls: List<String>): GetInquiryResult {
            return GetInquiryResult(
                inquiryId = inquiry.inquiryId,
                userId = inquiry.userId,
                type = inquiry.type.name,
                content = inquiry.content,
                status = inquiry.status.name,
                reply = inquiry.reply,
                imageUrls = imageUrls,
            )
        }
    }
}
