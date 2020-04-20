package com.owners.gravitas.util;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The Class UpdateValueUtil.
 *
 * @author vishwanathm
 */
public class UpdateValueUtil {

    /**
     * Instantiates a new update value util.
     */
    private UpdateValueUtil() {
        // do nothing
    }

    /**
     * Update number field.
     *
     * @param oldVal
     *            the old val
     * @param newVal
     *            the new val
     * @return the big decimal
     */
    public static Object updateField( final Object oldVal, final Object newVal ) {
        Object updatedVal = null;
        if (newVal != null && !newVal.equals( oldVal )) {
            updatedVal = newVal;
        }
        return updatedVal;
    }

    /**
     * Update field.
     *
     * @param oldVal
     *            the old val
     * @param newVal
     *            the new val
     * @return the object
     */
    public static String updateField( final String oldVal, final String newVal ) {
        String updatedVal = null;
        if (isNotBlank( newVal ) && !newVal.equalsIgnoreCase( oldVal )) {
            updatedVal = newVal;
        }
        return updatedVal;
    }
}
