package com.routebox.routebox.application.inquiry.dto

import InquiryType
import org.springframework.web.multipart.MultipartFile

data class CreateInquiryCommand(
    val userId: Long,
    val content: String,
    val inquiryType: InquiryType,
    val images: List<MultipartFile>?,
)
