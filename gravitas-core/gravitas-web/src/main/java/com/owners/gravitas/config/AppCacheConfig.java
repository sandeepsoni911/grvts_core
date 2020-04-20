package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * The Class AppCacheConfig.
 *
 * @author pabhishek
 */
@Configuration
@EnableCaching
public class AppCacheConfig {
    
    /* Redis */
    
    @Value( "${cache.redis.hostname}" )
    private String springRedisHostName;
    
    @Value( "${cache.redis.port}" )
    private int springRedisPort;

    @Value( "${cache.redis.databaseIndex}" )
    private int springRedisDatabase;

    @Value( "${cache.redis.default_expire_time_seconds}" )
    private long springRedisExpiration;
    
    @Value( "${cache.redis.timeout}" )
    private int springRedisTimeout;
    
    @Bean
    @Primary
    public CacheManager redisCacheManager(RedisTemplate redisTemplate) {
      RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
      cacheManager.setDefaultExpiration(springRedisExpiration);
      return cacheManager;
    }
    
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
      JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
      redisConnectionFactory.setHostName(springRedisHostName);
      redisConnectionFactory.setPort(springRedisPort);
      redisConnectionFactory.setDatabase(springRedisDatabase);
      redisConnectionFactory.setTimeout(springRedisTimeout);
      return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
      RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
      redisTemplate.setConnectionFactory(cf);
      return redisTemplate;
    }
    
    /*
    
    @Value( value = "${role.permission.cache.manager.name}" )
    private String rolePermissionCacheManagerName;

    @Value( value = "${cache.memoryStoreEvictionPolicy}" )
    private String memoryStoreEvictionPolicy;

    @Value( value = "${cache.maxEntriesLocalHeap}" )
    private long setMaxEntriesLocalHeap;

    @Value( value = "${cache.maxEntriesLocalDisk}" )
    private long setMaxEntriesLocalDisk;

    @Value( value = "${cache.timeToIdleSeconds}" )
    private long timeToIdleSeconds;

    @Value( value = "${cache.timeToLiveSeconds}" )
    private long timeToLiveSeconds;
    
    
    /**
     * Gets the eh cache manager.
     *
     * @return the eh cache manager
     */
    /*
    @Bean
    @Primary
    public CacheManager rolePermissionCacheManager() {
        return new EhCacheCacheManager( ehCacheManager() );
    }
    */
    /**
     * Eh cache manager.
     *
     * @return the net.sf.ehcache. cache manager
     */
    /*
    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        final CacheConfiguration cacheConfiguration = new CacheConfiguration();
        setGenericCachingProperties( cacheConfiguration );
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache( cacheConfiguration );
        return net.sf.ehcache.CacheManager.newInstance( config );
    }
	*/
    /**
     * Sets the generic caching properties.
     *
     * @param config
     *            the new generic caching properties
     */
    /*
    public void setGenericCachingProperties( final CacheConfiguration config ) {
        config.setName( rolePermissionCacheManagerName );
        config.setMemoryStoreEvictionPolicy( memoryStoreEvictionPolicy );
        config.setMaxEntriesLocalHeap( setMaxEntriesLocalHeap );
        config.setMaxEntriesLocalDisk( setMaxEntriesLocalDisk );
        config.timeToIdleSeconds( timeToIdleSeconds );
        config.timeToLiveSeconds( timeToLiveSeconds );
    }
    */
    
}
