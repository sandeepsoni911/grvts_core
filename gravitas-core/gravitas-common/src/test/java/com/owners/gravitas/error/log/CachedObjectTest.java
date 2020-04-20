package com.owners.gravitas.error.log;

import static org.junit.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * The Class CachedObjectTest.
 *
 * @author shivamm
 */
public class CachedObjectTest {

    /**
     * Test expire cache.
     */
    @Test
    public void testExpireCache() {
        CachedObject cachedObject = new CachedObject( new Object(), 1, 1 );
        cachedObject.expireCache();
    }

    /**
     * Test expire cache.
     */
    @Test
    public void testGetIdentifier() {
        CachedObject cachedObject = new CachedObject( new Object(), 2, 1 );
        assertEquals( cachedObject.getIdentifier(), 2 );
    }

    /**
     * Test is expired with date of expiration before current date.
     *
     * @throws InterruptedException
     */
    @Test
    public void testIsExpiredWithDateOfExpirationBeforeCurrentDate() throws InterruptedException {
        CachedObject cachedObject = new CachedObject( new Object(), 3, 1 );
        cachedObject.expireCache();
        Thread.sleep( 2000 );
        assertEquals( cachedObject.isExpired(), true );
    }

    /**
     * Test is expired with date of expiration after current date.
     */
    @Test
    public void testIsExpiredWithDateOfExpirationAfterCurrentDate() {
        CachedObject cachedObject = new CachedObject( new Object(), 4, 1 );
        assertEquals( cachedObject.isExpired(), false );
    }

    /**
     * Test is expired with minutes TO live zero.
     */
    @Test
    public void testIsExpiredWithMinutesTOLiveZero() {
        CachedObject cachedObject = new CachedObject( new Object(), 5, 0 );
        assertEquals( cachedObject.isExpired(), false );
    }
}
