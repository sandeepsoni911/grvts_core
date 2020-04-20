package com.owners.gravitas.config;

import static com.owners.gravitas.constants.Constants.GRAVITAS_CONFIG_DIR;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.spring3.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.service.util.ApplicationContextProvider;
import com.hubzu.cms.client.config.CmsClientSpringConfig;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class RootConfig is responsible for basic application configurations.
 *
 * @author manishd
 */
@Configuration
@ComponentScan( value = { "com.hubzu.dsync" } )
@ImportResource( "classpath:META-INF/amqp/gravitas-amqp.xml" )
@Import({ CmsClientSpringConfig.class })
@EnableRetry
public class RootConfig {

    /**
     * Method to create <code>PropertyPlaceholderConfigurer</code> bean by
     * reading properties files.
     *
     * @return configured {@link PropertyPlaceholderConfigurer} instance
     */
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertyPlaceholderConfigurer placeholderConfigurer = new PropertiesUtil();
        final Resource extResource = new FileSystemResource( new File( GRAVITAS_CONFIG_DIR, "gravitas.properties" ) );
        final Resource internalResource = new ClassPathResource( "gravitas-internal.properties" );
        final Resource oclResource = new ClassPathResource( "ocl.properties" );
        final Resource contactActivityResource = new ClassPathResource( "buyer-activity.properties" );
        placeholderConfigurer
                .setLocations( new Resource[] { contactActivityResource, oclResource, internalResource, extResource } );
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders( true );
        return placeholderConfigurer;
    }

    /**
     * Encryptable property placeholder configurer.
     *
     * @return the encryptable property placeholder configurer
     */
    @Bean
    public EncryptablePropertyPlaceholderConfigurer encryptablePropertyPlaceholderConfigurer() {
        final EnvironmentStringPBEConfig environment = new EnvironmentStringPBEConfig();
        environment.setAlgorithm( "PBEWithMD5AndDES" );
        environment.setPassword( "GRAVITAS_PASSWORD" );
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig( environment );
        final EncryptablePropertyPlaceholderConfigurer encryptablePropertyPlaceholderConfigurer = new EncryptablePropertyPlaceholderConfigurer(
                encryptor );
        final Resource res = new FileSystemResource( new File( GRAVITAS_CONFIG_DIR, "gravitas-db.properties" ) );
        encryptablePropertyPlaceholderConfigurer.setLocation( res );
        return encryptablePropertyPlaceholderConfigurer;
    }

    /**
     * Rest template bean.
     *
     * @return the rest template
     */
    @Bean( name = "restTemplate" )
    public RestTemplate restTemplate( @Value( value = "${rest.api.read.timeout.millis}" ) final int timeout ) {
        final RestTemplate proxyTemplate = new RestTemplate();
        final HttpMessageConverter< ? > stringHttpMessageConverternew = new StringHttpMessageConverter( UTF_8 );
        final HttpMessageConverter< ? > jacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        final List< HttpMessageConverter< ? > > convertors = new ArrayList<>();
        convertors.add( stringHttpMessageConverternew );
        convertors.add( jacksonHttpMessageConverter );
        proxyTemplate.setMessageConverters( convertors );

        final HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager( new PoolingHttpClientConnectionManager() ).build();
        final HttpComponentsClientHttpRequestFactory requestFactory = buildRequestFactory( httpClient, timeout );
        proxyTemplate.setRequestFactory( requestFactory );
        return proxyTemplate;
    }

    /**
     * Builds the request factory.
     *
     * @param httpClient
     *            the http client
     * @return the http components client http request factory
     */
    private HttpComponentsClientHttpRequestFactory buildRequestFactory( final HttpClient httpClient, final int timeout ) {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient );
        requestFactory.setConnectTimeout( timeout );
        requestFactory.setReadTimeout( timeout );
        return requestFactory;
    }

    /**
     * Api config.
     *
     * @param apiKey
     *            the api key
     * @param apiSecret
     *            the api secret
     * @param username
     *            the username
     * @param password
     *            the password
     * @return the api config
     */
    @Bean( name = "crmConfig" )
    public ApiConfig crmConfig( @Value( value = "${salesforce.api.key}" ) final String apiKey,
            @Value( value = "${salesforce.api.secret.key}" ) final String apiSecret,
            @Value( value = "${salesforce.api.username}" ) final String username,
            @Value( value = "${salesforce.api.password}" ) final String password ) {
        return new ApiConfig( apiKey, apiSecret, username, password );
    }

    /**
     * Message source.
     *
     * @return the reloadable resource bundle message source
     */
    @Bean( name = "messageSource" )
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename( "classpath:messages/messages" );
        messageBundle.setDefaultEncoding( "UTF-8" );
        return messageBundle;
    }

    /**
     * Object mapper bean.
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * Hubzu config.
     *
     * @param to
     *            the to
     * @param from
     *            the from
     * @return the notification email config
     */
    @Bean
    public NotificationEmailConfig hubzuConfig( @Value( value = "${affiliate.error.notification.to}" ) final String to,
            @Value( value = "${affiliate.error.notification.from}" ) final String from ) {
        return new NotificationEmailConfig( to, from );
    }

    /**
     * Owners config.
     *
     * @param to
     *            the to
     * @param from
     *            the from
     * @return the notification email config
     */
    @Bean
    public NotificationEmailConfig ownersConfig(
            @Value( value = "${affiliate.error.notification.owners.to}" ) final String to,
            @Value( value = "${affiliate.error.notification.owners.from}" ) final String from ) {
        return new NotificationEmailConfig( to, from );
    }

    /**
     * Valuations config.
     *
     * @param to
     *            the to
     * @param from
     *            the from
     * @return the notification email config
     */
    @Bean
    public NotificationEmailConfig valuationsConfig(
            @Value( value = "${affiliate.error.notification.valuations.to}" ) final String to,
            @Value( value = "${affiliate.error.notification.valuations.from}" ) final String from ) {
        return new NotificationEmailConfig( to, from );
    }

    /**
     * OCL config.
     *
     * @param to
     *            the to
     * @param from
     *            the from
     * @return the notification email config
     */
    @Bean
    public NotificationEmailConfig oclConfig(
            @Value( value = "${affiliate.error.notification.ocl.to}" ) final String to,
            @Value( value = "${affiliate.error.notification.ocl.from}" ) final String from ) {
        return new NotificationEmailConfig( to, from );
    }

    /**
     * Owners config for seller lead.
     *
     * @param to
     *            the to
     * @param from
     *            the from
     * @return the notification email config
     */
    @Bean
    public NotificationEmailConfig ownersSellerConfig(
            @Value( value = "${affiliate.sellerLead.error.notification.owners.to}" ) final String to,
            @Value( value = "${affiliate.sellerLead.error.notification.owners.from}" ) final String from ) {
        return new NotificationEmailConfig( to, from );
    }
    
    @Bean(name = "applicationContextProvider")
    public ApplicationContextProvider applicationContextProvder() throws Exception {
        ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        return applicationContextProvider;
    }
}
