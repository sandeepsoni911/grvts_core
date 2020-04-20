package com.owners.gravitas.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

public interface AmazonS3Service {

    void putFile(String uniqueFilePath, String bucketName, InputStream inputStream, String contentType,
            long contentLength)
            throws AmazonClientException, AmazonServiceException, IllegalArgumentException, IOException;

    public URL getSignedUrl(String objectKey, String bucketName, String contentType)
            throws AmazonClientException, AmazonServiceException, IllegalArgumentException, IOException;

    public void uploadUsingSignedUrl(String signedUrl, InputStream inputStream)
            throws AmazonClientException, AmazonServiceException, IllegalArgumentException, IOException;

}
