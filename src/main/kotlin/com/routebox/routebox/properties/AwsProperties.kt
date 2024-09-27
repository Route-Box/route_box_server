package com.routebox.routebox.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("aws")
data class AwsProperties(val s3: S3) {
    data class S3(
        val accessKey: String,
        val secretKey: String,
        val bucketName: String,
    )
}
