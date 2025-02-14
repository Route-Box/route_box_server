package com.routebox.routebox.infrastructure.inquiry

import com.routebox.routebox.domain.inquiry.InquiryImage
import org.springframework.data.jpa.repository.JpaRepository

interface InquiryImageRepository : JpaRepository<InquiryImage, Long> {
    fun findByInquiryId(inquiryId: Long): List<InquiryImage>
}
