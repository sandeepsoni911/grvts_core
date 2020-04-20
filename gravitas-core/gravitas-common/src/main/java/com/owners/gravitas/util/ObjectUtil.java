package com.owners.gravitas.util;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * The Class ObjectUtil.
 *
 * @author shivamm
 */
public class ObjectUtil {

    /** The Constant NULL_STR. */
    private final static String NULL_STR = "null";

    /**
     * Check for null.
     *
     * @param fieldValue
     *            the field value
     * @return the string
     */
    public static String isNull( final Object fieldValue ) {
        String value = EMPTY;
        if (fieldValue == null || NULL_STR.equals( fieldValue )) {
            return value;
        } else if (!NULL_STR.equals( fieldValue )) {
            value = String.valueOf( fieldValue );
        }
        return value;
    }
    
    /**
     * retrieve list of null property names
     * 
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames( Object source ) {
        final BeanWrapper src = new BeanWrapperImpl( source );
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set< String > emptyNames = new HashSet<>();
        for ( PropertyDescriptor pd : pds ) {
            // check if value of this property is null then add it to the
            // collection
            Object srcValue = src.getPropertyValue( pd.getName() );
            if (srcValue == null)
                emptyNames.add( pd.getName() );
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray( result );
    }
}
