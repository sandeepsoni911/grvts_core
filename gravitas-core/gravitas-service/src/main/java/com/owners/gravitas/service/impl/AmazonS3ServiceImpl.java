package com.owners.gravitas.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hubzu.common.logger.HubzuLog;
import com.owners.gravitas.service.AmazonS3Service;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    public static final HubzuLog LOGGER = HubzuLog.getLogger(AmazonS3ServiceImpl.class);
    private AmazonS3Client amazonS3Client;

    public AmazonS3ServiceImpl(String s3AccessKey, String s3SecretKey, int maxConnection,
            long maxConnectionIdleMillis) {
        this.initialise(s3AccessKey, s3SecretKey, maxConnection, maxConnectionIdleMillis);
    }

    private void initialise(String s3AccessKey, String s3SecretKey, int maxConnection, long maxConnectionIdleMillis) {
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setMaxConnections(maxConnection);
        clientConfig.setConnectionMaxIdleMillis(maxConnectionIdleMillis);
        BasicAWSCredentials credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
        this.amazonS3Client = new AmazonS3Client(credentials, clientConfig);
    }

    public AmazonS3ServiceImpl() {

    }

    @Override
    public void putFile(String uniqueFilePath, String bucketName, InputStream inputStream, String contentType,
            long contentLength)
            throws AmazonClientException, AmazonServiceException, IllegalArgumentException, IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(contentLength);
        this.amazonS3Client.putObject(bucketName, uniqueFilePath, inputStream, metadata);
        try {
            inputStream.close();
        } catch (IOException e) {
            LOGGER.error("Close Stream Error - uniqueFilePath : {}, bucketName : {}",
                    new Object[] { uniqueFilePath, bucketName });
        }

    }

    @Override
    public URL getSignedUrl(String objectKey, String bucketName, String contentType)
            throws AmazonClientException, AmazonServiceException, IllegalArgumentException, IOException {
        // Set the pre-signed URL to expire after 30 days.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60 * 30;
        expiration.setTime(expTimeMillis);

        // Generate the pre-signed URL.
        LOGGER.info("AmazonS3Service::getSignedUrl objectKey :{}, bucketName : {}, contentType : {}", objectKey,
                bucketName, contentType);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey)
                .withMethod(HttpMethod.PUT).withExpiration(expiration).withContentType(contentType);
        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        LOGGER.info("AmazonS3Service::getSignedUrl objectKey :{}, bucketName : {}, url : {}, contentType : {}",
                objectKey, bucketName, url, contentType);
        return url;
    }

    @Override
    public void uploadUsingSignedUrl(String signedUrl, InputStream inputStream)
            throws AmazonClientException, AmazonServiceException, IllegalArgumentException, IOException {
        // Create the connection and use it to upload the new object using the
        // pre-signed URL.
        LOGGER.info("AmazonS3Service::uploadUsingSignedUrl signedUrl :{}", signedUrl);
        URL url = new URL(signedUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        while (inputStream.available() != 0) {
            out.write(inputStream.read());
        }
        out.close();
        // Check the HTTP response code. To complete the upload and make the
        // object available,
        // you must interact with the connection object in some way.
        connection.getResponseCode();
        System.out.println("HTTP response code: " + connection.getResponseCode());
    }

}
