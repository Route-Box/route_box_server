package com.routebox.routebox.application.inquiry

import com.routebox.routebox.application.inquiry.dto.GetInquiryResult
import com.routebox.routebox.domain.inquiry.InquiryService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetInquiriesUseCase(
    private val inquiryService: InquiryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(userId: Long): List<GetInquiryResult> {
        val inquiriesWithImages = inquiryService.getInquiriesByUserId(userId)
        return inquiriesWithImages.map { inquiryWithImages ->
            GetInquiryResult.from(inquiryWithImages.inquiry, inquiryWithImages.imageUrls)
        }
    }
}
