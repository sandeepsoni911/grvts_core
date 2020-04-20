package com.owners.gravitas.error.log;

/**
 * The Interface Cacheable.
 * @author shivamm
 */
public interface Cacheable {

    /**
     * Expire cache.
     */
    public void expireCache();

    /**
     * Checks if is expired.
     *
     * @return true, if is expired
     */
    public boolean isExpired();

    /**
     * Gets the identifier.
     *
     * @return the identifier
     */
    public Object getIdentifier();
}
