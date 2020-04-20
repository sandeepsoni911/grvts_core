package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.machinelearning.AmazonMachineLearning;
import com.amazonaws.services.machinelearning.AmazonMachineLearningClient;
import com.amazonaws.services.machinelearning.AmazonMachineLearningClientBuilder;

/**
 * The Class AwsConfig.
 *
 * @author amits
 */
@Configuration
public class AwsConfig {

    /** The aws access key. */
    @Value( "${aws.accessKey}" )
    private String awsAccessKey;

    /** The aws secret key. */
    @Value( "${aws.secretKey}" )
    private String awsSecretKey;

    /** The aws region. */
    @Value( "${aws.machine.learning.region}" )
    private String awsRegion;

    /**
     * Amc client.
     *
     * @return the amazon machine learning client
     */
    @Bean( name = "amlClient" )
    public AmazonMachineLearning amlClient() {
        AmazonMachineLearningClientBuilder amcClientBuilder = AmazonMachineLearningClient.builder();
        amcClientBuilder.setRegion( awsRegion );
        return amcClientBuilder
                .withCredentials(
                        new AWSStaticCredentialsProvider( new BasicAWSCredentials( awsAccessKey, awsSecretKey ) ) )
                .build();
    }

}
