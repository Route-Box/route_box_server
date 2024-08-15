package com.routebox.routebox.infrastructure.common

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.routebox.routebox.domain.common.FileManager
import com.routebox.routebox.domain.common.dto.FileInfo
import com.routebox.routebox.properties.AwsProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Component
class AmazonS3FileManager(
    private val s3Client: AmazonS3,
    private val awsProperties: AwsProperties,
) : FileManager {
    override fun upload(file: MultipartFile, uploadPath: String): FileInfo {
        val originalFileName = file.originalFilename ?: ""
        val storedFileName = generateStoredFileName(originalFileName, uploadPath)

        s3Client.putObject(
            PutObjectRequest(
                awsProperties.s3.bucketName,
                storedFileName,
                file.inputStream,
                generateObjectMetadata(file),
            ).withCannedAcl(CannedAccessControlList.PublicRead),
        )
        val fileUrl = s3Client.getUrl(awsProperties.s3.bucketName, storedFileName).toString()

        return FileInfo(storedFileName, fileUrl)
    }

    private fun generateStoredFileName(originalFileName: String, uploadPath: String): String {
        val uuid = UUID.randomUUID().toString()

        val posOfExtension = originalFileName.lastIndexOf(".")
        if (posOfExtension == -1) {
            return uploadPath + uuid
        }

        val extension = originalFileName.substring(posOfExtension + 1)
        return "$uploadPath$uuid.$extension"
    }

    private fun generateObjectMetadata(file: MultipartFile) =
        ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = file.size
        }
}
