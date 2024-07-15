package com.routebox.routebox.domain.common

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class FileEntity protected constructor(
    storedFileName: String,
    fileUrl: String,
    deletedAt: LocalDateTime? = null,
) : BaseEntity() {
    var storedFileName: String = storedFileName
        protected set

    var fileUrl: String = fileUrl
        protected set

    var deletedAt: LocalDateTime? = deletedAt
        protected set
}
