package com.owners.gravitas.managers;

import java.util.List;

/**
 * This class is an manager class used to read and set {@link Object} as the
 * request parameters instance in thread local.
 *
 * @author vishwanathm
 */
public class ThreadLocalManager {

    /** Singleton instance of {@link ThreadLocalManager}. */
    private static final ThreadLocalManager INSTANCE = new ThreadLocalManager();

    /** The request params thread local. */
    private final ThreadLocal< List< Object > > requestParams = new ThreadLocal< List< Object > >();

    /**
     * Private constructor.
     */
    private ThreadLocalManager() {
    }

    /**
     * Method to get Singleton instance.
     *
     * @return {@link ThreadLocalManager} instance.
     */
    public static ThreadLocalManager getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the {@link Object} instance stored in the thread local
     * instance.
     *
     * @return Object {@link Object} instance.
     */
    public List< Object > getRequestParams() {
        return requestParams.get();
    }

    /**
     * Sets Object in thread local.
     *
     * @param requestParams
     *            the new request params
     */
    public void setRequestParams( final List< Object > requestParams ) {
        this.requestParams.set( requestParams );
    }

    /**
     * Removes last added {@link Object} from thread local.
     */
    public void removeRequestParams() {
        requestParams.remove();
    }
}
