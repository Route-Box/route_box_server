package com.routebox.routebox.application.inquiry.dto

import InquiryType
import com.routebox.routebox.domain.inquiry.Inquiry
import com.routebox.routebox.domain.inquiry.InquiryStatus

data class GetInquiryResult(
    val inquiryId: Long,
    val userId: Long,
    val type: InquiryType,
    val content: String,
    val status: InquiryStatus,
    val reply: String?,
    val imageUrls: List<String>,
    val createdAt: String,
) {
    companion object {
        fun from(inquiry: Inquiry, imageUrls: List<String>): GetInquiryResult {
            return GetInquiryResult(
                inquiryId = inquiry.inquiryId,
                userId = inquiry.userId,
                type = inquiry.type,
                content = inquiry.content,
                status = inquiry.status,
                reply = inquiry.reply,
                imageUrls = imageUrls,
                createdAt = inquiry.createdAt.toString(),
            )
        }
    }
}
