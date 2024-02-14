package com.example.log_puller.utility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class S3ClientConfiguration {
    private final PropertyReader propertyReader;
    @Autowired
    S3ClientConfiguration(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }
    public AmazonS3 createS3Client() {

        String accessKey = propertyReader.getProperty("aws.access.key");
        String secretKey = propertyReader.getProperty("aws.secret.key");

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
