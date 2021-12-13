package com.jira.test.demo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Component;

@Component
public class FileS3Service {
    BasicAWSCredentials creds = new BasicAWSCredentials("accessKey", "secretKey");
    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
           .withCredentials(new AWSStaticCredentialsProvider(creds))
            .withRegion(Regions.US_WEST_1)
            .build();

    public S3Object getObject(String bucketName, String keyName) {
        try {
            return s3.getObject(bucketName, keyName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            return null;
        }
    }
}
