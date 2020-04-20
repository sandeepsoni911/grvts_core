package com.owners.gravitas.auth.util;

import java.io.InputStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

/**
 * The Class FirebaseConfig provides the firebase access configuration.
 */
public class FirebaseConfig {

    /** The Constant FIREBASE_ACCESS_FILENAME_PREFIX. */
    private static final String FIREBASE_ACCESS_FILENAME_PREFIX = "firebase-access";

    /** The Constant S3_BUCKET_NAME. */
    private static final String S3_BUCKET_NAME = "gravitas-authorizer-config";

    /**
     * Gets the access config.
     *
     * @param functionName
     *            the function name
     * @return the access config
     */
    public InputStream getAccessConfig( final String functionName ) {
        AmazonS3 s3Client = new AmazonS3Client();
        S3Object object = s3Client.getObject( new GetObjectRequest( S3_BUCKET_NAME, getFileName( functionName ) ) );
        return object.getObjectContent();
    }

    /**
     * Gets the file name.
     *
     * @param functionName
     *            the function name
     * @return the file name
     */
    private String getFileName( final String functionName ) {
        String suffix = ".json";
        if (functionName.toLowerCase().contains( "prod" )) {
            suffix = "-prod.json";
        } else if (functionName.toLowerCase().contains( "integ" )) {
            suffix = "-integ.json";
        } else if (functionName.toLowerCase().contains( "qa" )) {
            suffix = "-qa.json";
        } else if (functionName.toLowerCase().contains( "dev" )) {
            suffix = "-dev.json";
        }
        System.out.println( "Loading firebase file:" + FIREBASE_ACCESS_FILENAME_PREFIX + suffix );
        return FIREBASE_ACCESS_FILENAME_PREFIX + suffix;
    }
}
