package com.routebox.routebox.application.inquiry

import com.routebox.routebox.application.inquiry.dto.GetInquiryResult
import com.routebox.routebox.domain.inquiry.InquiryService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetInquiryUseCase(
    private val inquiryService: InquiryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(inquiryId: Long): GetInquiryResult {
        val inquiryWithImages = inquiryService.getInquiryById(inquiryId)
        return GetInquiryResult.from(inquiryWithImages.inquiry, inquiryWithImages.imageUrls)
    }
}
