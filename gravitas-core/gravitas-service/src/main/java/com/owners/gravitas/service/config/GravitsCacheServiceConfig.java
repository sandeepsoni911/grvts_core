package com.owners.gravitas.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hubzu.common.cache.CacheManager;
import com.hubzu.common.cache.config.CacheConfig;
import com.hubzu.common.cache.service.CacheService;
import com.hubzu.common.logger.HubzuLog;

@Configuration
public class GravitsCacheServiceConfig {

    private static final HubzuLog LOGGER = HubzuLog.getLogger( GravitsCacheServiceConfig.class );

    /** The host url. */
    @Value( "${dsync.redis.host-name}" )
    private String hostName;

    /** The password. */
    @Value( "${dsync.redis.password}" )
    private String password;

    /** The port. */
    @Value( "${dsync.redis.port}" )
    private int port;

    /** The timeout. */
    @Value( "${dsync.redis.timeout}" )
    private int timeout = 8000;

    /** The database. */
    @Value( "${dsync.redis.databaseIndex}" )
    private int database = 0;

    /** The is enabled. */
    private @Value( "${gravitas.redis_cache.connection_pool_enable:true}" ) boolean isPoolEnabled;

    /** The redis pool max idle. */
    private @Value( "${gravitas.redis_cache.connection_pool_max_idle:40}" ) int redisPoolMaxIdle;

    /** The redis pool min idle. */
    private @Value( "${gravitas.redis_cache.connection_pool_min_idle:20}" ) int redisPoolMinIdle;

    /** The redis pool max total. */
    private @Value( "${gravitas.redis_cache.connection_pool_max_total:200}" ) int redisPoolMaxTotal;

    /** The redis pool max wait. */
    private @Value( "${gravitas.redis_cache.connection_pool_max_wait_millis:-1}" ) int redisPoolMaxWait;

    /**
     * Initialize cache.
     * 
     * @return
     */
    @Bean
    public CacheService gravitasCacheService() {
        LOGGER.debug( "Initializing gravitas cache" );
        CacheConfig cacheConfig = new CacheConfig( hostName, password, port, timeout, database, isPoolEnabled );
        if (isPoolEnabled) {
            cacheConfig.setPoolMaxTotal( redisPoolMaxTotal );
            cacheConfig.setPoolMaxIdle( redisPoolMaxIdle );
            cacheConfig.setPoolMinIdle( redisPoolMinIdle );
            cacheConfig.setPoolMaxWait( redisPoolMaxWait );
        }
        return CacheManager.getCacheService( cacheConfig );
    }
}
