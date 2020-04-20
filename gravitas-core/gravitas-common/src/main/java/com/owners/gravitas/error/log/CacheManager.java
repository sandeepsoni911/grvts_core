package com.owners.gravitas.error.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class CacheManager.
 *
 * @author shivamm
 */
public class CacheManager {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CacheManager.class );

    /** The cache hash map. */
    private static java.util.HashMap cacheHashMap = new java.util.HashMap();
    static {
        try {
            Thread threadCleanerUpper = new Thread( new Runnable() {
                int milliSecondSleepTime = 5000;
                public void run() {
                    try {
                        while ( true ) {
                            Thread.sleep( 1 );
                            java.util.Set keySet = cacheHashMap.keySet();
                            java.util.Iterator keys = keySet.iterator();
                            while ( keys.hasNext() ) {
                                Object key = keys.next();
                                Cacheable value = ( Cacheable ) cacheHashMap.get( key );
                                if (value.isExpired()) {
                                    cacheHashMap.remove( key );
                                    LOGGER.debug( "Found expire cache objects..." );
                                }
                            }
                            Thread.sleep( this.milliSecondSleepTime );
                        }
                    } catch ( Exception e ) {
                        LOGGER.error( "Something went wrong in CacheManager.Static Block expiry: " + e );
                    }
                    return;
                }
            } );
            threadCleanerUpper.setPriority( Thread.MIN_PRIORITY );
            threadCleanerUpper.start();
        } catch ( Exception e ) {
            LOGGER.error( "Something went wrong in CacheManager.Static Block: " + e );
        }
    }

    /**
     * Instantiates a new cache manager.
     */
    public CacheManager() {
    }

    /**
     * Put cache.
     *
     * @param object
     *            the object
     */
    public static void putCache( Cacheable object ) {
        cacheHashMap.put( object.getIdentifier(), object );
    }

    /**
     * Gets the cache.
     *
     * @param identifier
     *            the identifier
     * @return the cache
     */
    public static Cacheable getCache( Object identifier ) {
        Cacheable object = ( Cacheable ) cacheHashMap.get( identifier );
        if (object == null)
            return null;
        if (object.isExpired()) {
            cacheHashMap.remove( identifier );
            return null;
        } else {
            return object;
        }
    }
}
