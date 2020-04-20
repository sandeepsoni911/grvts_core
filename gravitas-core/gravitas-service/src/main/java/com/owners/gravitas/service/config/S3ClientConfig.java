package com.owners.gravitas.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.owners.gravitas.service.AmazonS3Service;
import com.owners.gravitas.service.impl.AmazonS3ServiceImpl;

@Configuration
@ComponentScan( basePackages = { "com.owners.gravitas.service" } )
public class S3ClientConfig {
    @Value( "${gravitas.amazon.s3.tour_feedback.accessKey}" )
    private String s3AccessKey;
    @Value( "${gravitas.amazon.s3.tour_feedback.secretKey}" )
    private String s3SecretKey;
    @Value( "${gravitas.amazon.s3.tour_feedback.max_connection:100}" )
    private int maxConnection;
    @Value( "${gravitas.amazon.s3.tour_feedback.max_connection_idle_millis:120000}" )
    private long maxConnectionIdleMillis;

    @Bean
    public AmazonS3Service amazonS3Service() {
        AmazonS3ServiceImpl amazonS3Service = new AmazonS3ServiceImpl( this.s3AccessKey, this.s3SecretKey,
                this.maxConnection, this.maxConnectionIdleMillis );
        return amazonS3Service;
    }
}
