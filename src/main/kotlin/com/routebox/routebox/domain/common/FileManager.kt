package com.routebox.routebox.domain.common

import com.routebox.routebox.domain.common.dto.FileInfo
import org.springframework.web.multipart.MultipartFile

interface FileManager {
    fun upload(file: MultipartFile, uploadPath: String): FileInfo
}
