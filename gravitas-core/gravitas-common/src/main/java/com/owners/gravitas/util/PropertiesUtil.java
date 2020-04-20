package com.owners.gravitas.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * This class store all the key value pairs of property files in a map, so that
 * easily we can iterate and retrieve the required value w.r.t key.
 *
 * @author vishwanathm
 */
public class PropertiesUtil extends PropertyPlaceholderConfigurer {

    /** The properties map. */
    private static Map< String, String > propertiesMap = new HashMap< String, String >();

    /** Default as in PropertyPlaceholderConfigurer. */
    private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
     * #setSystemPropertiesMode(int)
     */
    @Override
    public void setSystemPropertiesMode( final int systemPropertiesMode ) {
        super.setSystemPropertiesMode( systemPropertiesMode );
        springSystemPropertiesMode = systemPropertiesMode;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#
     * processProperties(org.springframework.
     * beans.factory.config.ConfigurableListableBeanFactory,
     * java.util.Properties)
     */
    @Override
    protected void processProperties( final ConfigurableListableBeanFactory beanFactory, final Properties props ) {
        super.processProperties( beanFactory, props );
        for ( Object key : props.keySet() ) {
            final String keyStr = key.toString();
            final String valueStr = resolvePlaceholder( keyStr, props, springSystemPropertiesMode );
            propertiesMap.put( keyStr, valueStr );
        }
    }

    /**
     * Get the value from property map.
     *
     * @param name
     *            the key to get its value
     * @return the value of passed key.
     */
    public static String getProperty( final String name ) {
        return propertiesMap.get( name ) != null ? propertiesMap.get( name ).toString() : null;
    }

    /**
     * Gets the properties map.
     *
     * @return the properties map
     */
    public static Map< String, String > getPropertiesMap() {
        return propertiesMap;
    }

    /**
     * Sets the properties map.
     *
     * @param newPropertiesMap
     *            the new properties map
     */
    public static void setPropertiesMap( final Map< String, String > newPropertiesMap ) {
        propertiesMap = newPropertiesMap;
    }

    /**
     * Gets the loan phase property prefix.
     *
     * @param loanPhase
     *            the loan phase
     * @return the loan phase property prefix
     */
    public static String getLoanPhasePropertyPrefix( final String loanPhase ) {
        return loanPhase.replace( "-", "." ).replace( "/", "." ).replace( " ", "." ).toLowerCase();
    }

}
