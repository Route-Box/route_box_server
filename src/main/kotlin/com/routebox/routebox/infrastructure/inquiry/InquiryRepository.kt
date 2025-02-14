package com.routebox.routebox.infrastructure.inquiry

import com.routebox.routebox.domain.inquiry.Inquiry
import org.springframework.data.jpa.repository.JpaRepository

interface InquiryRepository : JpaRepository<Inquiry, Long> {
    fun findByUserId(userId: Long): List<Inquiry>
}
