package com.routebox.routebox.controller.inquiry.dto

import com.routebox.routebox.application.inquiry.dto.GetInquiryResult

data class GetInquiryResponse(
    val inquiries: List<InquiryDto>,
) {
    companion object {
        fun from(inquiries: List<GetInquiryResult>): GetInquiryResponse {
            return GetInquiryResponse(
                inquiries = inquiries.map { InquiryDto.from(it) },
            )
        }
    }
}
