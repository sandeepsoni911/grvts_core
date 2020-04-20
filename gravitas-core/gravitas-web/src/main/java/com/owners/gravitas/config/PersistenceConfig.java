package com.owners.gravitas.config;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * This class is responsible for Persistence related configuration.
 *
 * @author vishwanathm
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( basePackages = { "com.owners.gravitas.repository", "com.hubzu.dsync.repository" } )
@EnableJpaAuditing( auditorAwareRef = "auditor" )
public class PersistenceConfig implements TransactionManagementConfigurer {

    /** Database driver name. */
    @Value( "${db.driver}" )
    private String driver;

    /** Database URL. */
    @Value( "${db.url}" )
    private String url;

    /** Database user name. */
    @Value( "${db.username}" )
    private String username;

    /** Database Password. */
    @Value( "${db.password}" )
    private String password;

    /** show sql. */
    @Value( "${db.showsql}" )
    private boolean showSql;

    /** Database vendor specific Dialect. */
    @Value( "${database.hibernate.dialect}" )
    private String dialect;

    /** Database validation query. */
    @Value( "${db.validationQuery}" )
    private String validationQuery;

    /**
     * Indication of whether objects will be validated before being borrowed
     * from the pool.
     */
    @Value( "${db.testOnBorrow}" )
    private boolean testOnBorrow;

    /**
     * Indicate query validation should take place while the connection is idle.
     */
    @Value( "${db.testWhileIdle}" )
    private boolean testWhileIdle;

    /**
     * Indicates number of milliseconds to sleep between runs of the idle
     * connection validation.
     */
    @Value( "${db.timeBetweenEvictionRunsMillis}" )
    private int timeBetweenEvictionRunsMillis;

    /**
     * Indicates maximum number of active connections that can be allocated from
     * this pool at the same time.
     */
    @Value( "${db.maxActiveConnectionInPool}" )
    private int maxActiveConnectionInPool;

    /**
     * Indicates number of connections that will be established when the
     * connection pool is started.
     */
    @Value( "${db.initialPoolSize}" )
    private int initialPoolSize;

    /**
     * Indicates maximum number of milliseconds that the pool will wait for a
     * connection to be returned before throwing an exception.
     */
    @Value( "${db.maxPoolWaitMillis}" )
    private int maxPoolWaitMillis;

    /**
     * Indicates time in seconds before a connection can be considered
     * abandoned.
     */
    @Value( "${db.connectionAbandonedTimeoutSecs}" )
    private int connectionAbandonedTimeoutSecs;

    /**
     * Indicates minimum number of established connections that should be kept
     * in the pool at all times.
     */
    @Value( "${db.minIdleConnectionInPool}" )
    private int minIdleConnectionInPool;

    /**
     * This method is datasource bean provider.
     *
     * @return datasource bean
     */
    @Bean
    public DataSource dataSource() {
        final PoolProperties props = new PoolProperties();
        props.setUrl( url );
        props.setDriverClassName( driver );
        props.setUsername( username );
        props.setPassword( password );
        props.setJmxEnabled( true );
        props.setTestWhileIdle( testWhileIdle );
        props.setTestOnBorrow( testOnBorrow );
        props.setValidationQuery( validationQuery );
        props.setTimeBetweenEvictionRunsMillis( timeBetweenEvictionRunsMillis );
        props.setMaxActive( maxActiveConnectionInPool );
        props.setInitialSize( initialPoolSize );
        props.setMaxWait( maxPoolWaitMillis );
        props.setRemoveAbandonedTimeout( connectionAbandonedTimeoutSecs );
        props.setMinIdle( minIdleConnectionInPool );
        props.setLogAbandoned( true );
        props.setRemoveAbandoned( true );
        props.setJdbcInterceptors( "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer" );
        final DataSource datasource = new DataSource();
        datasource.setPoolProperties( props );
        return datasource;
    }

    /**
     * Entity manager factory.
     *
     * @return the local container entity manager factory bean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource( dataSource() );
        entityManagerFactoryBean.setPackagesToScan( "com.owners.gravitas.domain.entity", "com.hubzu.dsync.entity" );
        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql( showSql );
        entityManagerFactoryBean.setJpaVendorAdapter( jpaVendorAdapter );

        final Properties jpaProperties = new Properties();
        jpaProperties.put( org.hibernate.cfg.Environment.DIALECT, dialect );
        entityManagerFactoryBean.setJpaProperties( jpaProperties );
        return entityManagerFactoryBean;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.transaction.annotation.
     * TransactionManagementConfigurer#annotationDrivenTransactionManager()
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }

    /**
     * This method is Transaction Manager bean provider.
     *
     * @return transactionmanager bean
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
        return transactionManager;
    }

    /**
     * This method is Exception Translator bean provider.
     *
     * @return Exception Translator bean
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Jdbc template.
     *
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate( dataSource() );
    }
}
