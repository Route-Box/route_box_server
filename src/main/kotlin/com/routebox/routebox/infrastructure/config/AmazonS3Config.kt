package com.routebox.routebox.infrastructure.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.routebox.routebox.properties.AwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config(private val awsProperties: AwsProperties) {
    @Bean
    fun amazonS3Client(): AmazonS3 =
        AmazonS3ClientBuilder.standard()
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(
                        awsProperties.s3.accessKey,
                        awsProperties.s3.secretKey,
                    ),
                ),
            ).withRegion(Regions.AP_NORTHEAST_2).build()
            ?: throw NoSuchElementException("AWS S3 Bucket에 엑세스 할 수 없습니다. 엑세스 정보와 bucket name을 다시 확인해주세요.")
}
