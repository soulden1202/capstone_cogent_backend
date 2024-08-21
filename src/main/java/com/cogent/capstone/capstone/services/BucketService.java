package com.cogent.capstone.capstone.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BucketService {

    @Autowired
    AmazonS3 s3Client;

    @Value("${aws.bucket.prefix}")
    String URL_PREFIX;

    @Value("${aws.bucket.name}")
    String bucketName;

    public List<Bucket> getBucketList() {

        return s3Client.listBuckets();
    }

    public boolean validateBucket(String bucketName) {
        List<Bucket> bucketList = getBucketList();

        return bucketList.stream().anyMatch(m -> bucketName.equals(m.getName()));
    }

    public String getObjectFromBucket(String objectName) throws IOException {

        return URL_PREFIX + objectName;

    }

    public void createBucket(String bucketName) {
        s3Client.createBucket(bucketName);
    }

    public PutObjectResult putObjectIntoBucket(MultipartFile file) throws SdkClientException, IOException {

        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentType(file.getContentType());

        try {
            return s3Client.putObject(bucketName,
                    file.getOriginalFilename(), file.getInputStream(),
                    metadata);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            return null;
        }

    }

    public void deleteObject(String key) {

        s3Client.deleteObject(bucketName, key);
    }
}