package com.owners.gravitas.managers;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an manager class used to read and set {@link Object} as the
 * request parameters instance in thread local.
 *
 * @author vishwanathm
 */
public class RequestParameterThreadLocalManager {

    /** Singleton instance of {@link RequestParameterThreadLocalManager}. */
    private static final RequestParameterThreadLocalManager INSTANCE = new RequestParameterThreadLocalManager();

    /** Default identity instance. */
    private final Map< String, Object > defaultRequestObj = new HashMap<>();

    /** The request params thread loacal. */
    private final ThreadLocal< Map< String, Object > > requestParamsThreadLoacal = new ThreadLocal< Map< String, Object > >() {
        @Override
        protected Map< String, Object > initialValue() {
            return defaultRequestObj;
        }
    };

    /** The agent info. */
    private final ThreadLocal< Object > agentInfoThreadLocal = new ThreadLocal< Object >() {
        @Override
        protected Object initialValue() {
            return defaultRequestObj;
        }
    };

    /**
     * Private constructor.
     */
    private RequestParameterThreadLocalManager() {
    }

    /**
     * Method to get Singleton instance.
     *
     * @return {@link RequestParameterThreadLocalManager} instance.
     */
    public static RequestParameterThreadLocalManager getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the {@link Object} instance stored in the thread local
     * instance.
     *
     * @return Object {@link Object} instance.
     */
    public Map< String, Object > getRequestObj() {
        return requestParamsThreadLoacal.get();
    }

    /**
     * Gets the agent info.
     *
     * @return the agent info
     */
    public Object getAgentInfo() {
        return agentInfoThreadLocal.get();
    }

    /**
     * Sets Object in thread local.
     *
     * @param defaultRequestObj
     *            {@link Object}
     */
    public void setRequestObj( final String key, final Object requestObj ) {
        requestParamsThreadLoacal.get().put( key, requestObj );
    }

    /**
     * Sets the agent info.
     *
     * @param agentInfo
     *            the new agent info
     */
    public void setAgentInfo( final Object agentInfo ) {
        this.agentInfoThreadLocal.set( agentInfo );
    }

    /**
     * Removes last added {@link Object} from thread local.
     */
    public void removeRequestObj() {
        requestParamsThreadLoacal.remove();
    }

    /**
     * Removes the agent info.
     */
    public void removeAgentInfo() {
        agentInfoThreadLocal.remove();
    }

    /**
     * This method is used to check whether current instance is with default
     * values or not.
     *
     * @param defaultRequestObj
     *            - {@link Object} instance.
     * @return boolean indicating default instance or not.
     */
    public boolean isDefaultRequestParam( final Object requestObj ) {
        return defaultRequestObj.equals( requestObj );
    }
}
