package com.owners.gravitas.error.log;

/**
 * The Class CachedObject.
 * @author shivamm
 */
public class CachedObject implements Cacheable {

    /** The dateof expiration. */
    private java.util.Date dateofExpiration = null;

    /** The identifier. */
    private Object identifier = null;

    /** The object. */
    public Object object = null;

    /**
     * Instantiates a new cached object.
     *
     * @param obj the obj
     * @param id the id
     * @param minutesToLive the minutes to live
     */
    public CachedObject( Object obj, Object id, int minutesToLive ) {
        this.object = obj;
        this.identifier = id;
        // minutesToLive of 0 means it lives on indefinitely.
        if (minutesToLive != 0) {
            dateofExpiration = new java.util.Date();
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime( dateofExpiration );
            cal.add( cal.MINUTE, minutesToLive );
            dateofExpiration = cal.getTime();
        }
    }

    /* (non-Javadoc)
     * @see Cacheable#isExpired()
     */
    public void expireCache() {
        dateofExpiration = new java.util.Date();
    }

    /* (non-Javadoc)
     * @see Cacheable#getIdentifier()
     */
    public Object getIdentifier() {
        return identifier;
    }

    @Override
    public boolean isExpired() {
        if (dateofExpiration != null) {
          if (dateofExpiration.before( new java.util.Date() )) {
              return true;
          } else {
              return false;
          }
      } else
          return false;
    }
}
