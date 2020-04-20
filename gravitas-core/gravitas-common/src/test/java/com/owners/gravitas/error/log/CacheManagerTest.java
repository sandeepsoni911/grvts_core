package com.owners.gravitas.error.log;

import static org.junit.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * The Class CacheManagerTest.
 *
 * @author shivamm
 */
public class CacheManagerTest {

    /**
     * Test put cache.
     */
    @Test
    public void testPutCache() {
        CachedObject newCache = new CachedObject( new Object(), 6, 0 );
        CacheManager.putCache( newCache );
    }

    /**
     * Test get cache with object not null.
     */
    @Test
    public void testGetCacheWithObjectNotNull() {
        CacheManager cacheManager = new CacheManager();
        String test = "test";
        CachedObject newCache = new CachedObject( test, 6, 0 );
        CacheManager.putCache( newCache );
        CachedObject cache = ( CachedObject ) CacheManager.getCache( 6 );
        cache.expireCache();
        assertEquals( cache.object, test );
    }

    /**
     * Test get cache with object null.
     *
     * @throws InterruptedException
     */
    @Test
    public void testGetCacheWithObjectNull() throws InterruptedException {
        CachedObject newCache = new CachedObject( null, 7, 0 );
        CacheManager.putCache( newCache );
        newCache.expireCache();
        Thread.sleep( 2 );
        CachedObject cache = ( CachedObject ) CacheManager.getCache( 7 );
        assertEquals( cache, null );
    }
}
