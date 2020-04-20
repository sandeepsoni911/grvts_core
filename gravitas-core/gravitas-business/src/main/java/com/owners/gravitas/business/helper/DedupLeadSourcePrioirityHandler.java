package com.owners.gravitas.business.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class DedupLeadSourcePrioirityHandler.
 *
 * @author vishwanathm
 */
@Component
public class DedupLeadSourcePrioirityHandler {

    /** The affiliate sourcesInPriority str. */
    @Value( "${owners.dedupe.affiliate.sources}" )
    private String affiliateSourcesStr;

    /** The Constant ANY_SOURCE. */
    private static final String ANY_SOURCE = "ANY_SOURCE_VALUE";

    /** The Constant ANY_AFFILIATED_EMAIL. */
    private static final String ANY_AFFILIATED_EMAIL = "ANY_AFFILIATED_EMAIL";

    /** The Constant LEAD_SOURCE_CONFIG_PREFIX. */
    private static final String LEAD_SOURCE_CONFIG_PREFIX = "owners.dedupe.sources.priority.";

    /** The lead sources. */
    private Map< String, Integer > leadSources = new HashMap<>();

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        final Set< Entry< String, String > > props = PropertiesUtil.getPropertiesMap().entrySet();
        String propKey;
        for ( Entry< String, String > prop : props ) {
            propKey = prop.getKey();
            if (propKey.startsWith( LEAD_SOURCE_CONFIG_PREFIX )) {
                final Integer priority = Integer.valueOf( propKey.substring(
                        propKey.indexOf( LEAD_SOURCE_CONFIG_PREFIX ) + LEAD_SOURCE_CONFIG_PREFIX.length() ) );
                leadSources.put( prop.getValue().toLowerCase(), priority );
            }
        }
    }

    /**
     * Checks if is source update required.
     *
     * 1) Random or UNKNOWN source falls under highest priority.
     * 2) True : If new source priority index is less than old source
     * priority.
     * 3) Affiliate email sources fall between index 3 and 5.
     *
     * @param newSource
     *            the new source string in lower case.
     * @param oldSource
     *            the old source string in lower case.
     * @return return true: if new source priority index is less than old
     *         source
     *         priority.
     */
    public boolean isSourceUpdateRequired( final String newLeadSource, final String oldLeadSource ) {
        return getPriority( newLeadSource ) <= getPriority( oldLeadSource );
    }

    /**
     * Gets the priority.
     *
     * @param leadSource
     *            the lead source
     * @return the priority
     */
    private int getPriority( final String leadSource ) {
        String source = leadSource;
        if (affiliateSourcesStr.toLowerCase().contains( leadSource )) {
            source = ANY_AFFILIATED_EMAIL.toLowerCase();
        }
        Integer priority = leadSources.get( source );
        if (priority == null) {
            priority = leadSources.get( ANY_SOURCE.toLowerCase() );
        }
        return priority;
    }
}
