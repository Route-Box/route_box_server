package com.routebox.routebox.application.inquiry

import com.routebox.routebox.application.inquiry.dto.CreateInquiryCommand
import com.routebox.routebox.domain.inquiry.Inquiry
import com.routebox.routebox.domain.inquiry.InquiryService
import com.routebox.routebox.domain.inquiry.InquiryStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateInquiryUseCase(
    private val inquiryService: InquiryService,
) {
    @Transactional
    operator fun invoke(command: CreateInquiryCommand): Long {
        val inquiry = Inquiry(
            userId = command.userId,
            type = command.inquiryType,
            content = command.content,
            reply = null,
            status = InquiryStatus.PENDING,
        )
        return inquiryService.createInquiry(inquiry, command.images)
    }
}
