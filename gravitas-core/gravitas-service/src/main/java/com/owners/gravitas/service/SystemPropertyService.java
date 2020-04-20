package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.SystemProperty;

/**
 * The Interface SystemPropertyService.
 */
public interface SystemPropertyService {

    /**
     * Gets the property.
     *
     * @param property
     *            the property
     * @return the property
     */
    SystemProperty getProperty( String property );

    /**
     * Gets the properties.
     *
     * @param names
     *            the names
     * @return the properties
     */
    List< SystemProperty > getProperties( List< String > names );

}
