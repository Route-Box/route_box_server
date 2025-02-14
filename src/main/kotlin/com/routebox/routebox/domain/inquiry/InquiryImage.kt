package com.routebox.routebox.domain.inquiry

import com.routebox.routebox.domain.common.TimeTrackedBaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "inquiry_image")
data class InquiryImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_image_id")
    val inquiryImageId: Long = 0,

    @Column(name = "inquiry_id", nullable = false)
    val inquiryId: Long,

    @Column(name = "stored_file_name", nullable = false)
    val storedFileName: String,

    @Column(name = "file_url", nullable = false)
    val fileUrl: String,
) : TimeTrackedBaseEntity()
