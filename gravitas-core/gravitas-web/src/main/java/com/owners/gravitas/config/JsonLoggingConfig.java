package com.owners.gravitas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class JsonLoggingConfig.
 *
 * @author vishwanathm
 */
@Configuration
@EnableAspectJAutoProxy
public class JsonLoggingConfig {

    /**
     * Mapper.
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }
}
