package com.routebox.routebox.domain.inquiry

import com.routebox.routebox.domain.common.FileManager
import com.routebox.routebox.exception.inquiry.InquiryNotFoundException
import com.routebox.routebox.infrastructure.inquiry.InquiryImageRepository
import com.routebox.routebox.infrastructure.inquiry.InquiryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class InquiryService(
    private val inquiryRepository: InquiryRepository,
    private val inquiryImageRepository: InquiryImageRepository,
    private val fileManager: FileManager,
) {
    companion object {
        const val INQUIRY_IMAGE_UPLOAD_PATH = "inquiry-images/"
    }

    @Transactional
    fun createInquiry(inquiry: Inquiry, images: List<MultipartFile>?): Long {
        val savedInquiry = inquiryRepository.save(inquiry)
        images?.forEach { image ->
            try {
                val (storedFileName, fileUrl) = fileManager.upload(
                    image,
                    INQUIRY_IMAGE_UPLOAD_PATH,
                )
                val inquiryImage = InquiryImage(
                    inquiryId = savedInquiry.inquiryId,
                    storedFileName = storedFileName,
                    fileUrl = fileUrl,
                )
                inquiryImageRepository.save(inquiryImage)
            } catch (e: IOException) {
                throw RuntimeException("Failed to upload inquiry image", e)
            }
        }
        return savedInquiry.inquiryId
    }

    @Transactional(readOnly = true)
    fun getInquiriesByUserId(userId: Long): List<InquiryWithImages> {
        val inquiries = inquiryRepository.findByUserId(userId)
        return inquiries.map { inquiry ->
            val images = inquiryImageRepository.findByInquiryId(inquiry.inquiryId)
            InquiryWithImages(inquiry, images.map { it.fileUrl })
        }
    }

    @Transactional(readOnly = true)
    fun getInquiryById(inquiryId: Long): InquiryWithImages {
        val inquiry = inquiryRepository.findById(inquiryId).orElseThrow {
            throw InquiryNotFoundException()
        }
        val images = inquiryImageRepository.findByInquiryId(inquiryId)
        return InquiryWithImages(inquiry, images.map { it.fileUrl })
    }
}

data class InquiryWithImages(
    val inquiry: Inquiry,
    val imageUrls: List<String>,
)
