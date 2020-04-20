package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class ClientStatisticsConfig.
 *
 * @author raviz
 */
@ManagedResource( objectName = "com.owners.gravitas:name=ClientStatisticsConfig" )
@Component
public class ClientStatisticsConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The from date. */
    @Value( "${agent.statistics.from.date:2016-01-01}" )
    private String fromDate;

    /** The to date. */
    @Value( "${agent.statistics.to.date:2020-01-01}" )
    private String toDate;

    /**
     * Gets the from date.
     *
     * @return the from date
     */
    @ManagedAttribute
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Sets the from date.
     *
     * @param fromDate
     *            the new from date
     */
    @ManagedAttribute
    public void setFromDate( final String fromDate ) {
        this.fromDate = fromDate;
        propertyWriter.saveJmxProperty( "agent.statistics.from.date", fromDate );
    }

    /**
     * Gets the to date.
     *
     * @return the to date
     */
    @ManagedAttribute
    public String getToDate() {
        return toDate;
    }

    /**
     * Sets the to date.
     *
     * @param toDate
     *            the new to date
     */
    @ManagedAttribute
    public void setToDate( final String toDate ) {
        this.toDate = toDate;
        propertyWriter.saveJmxProperty( "agent.statistics.to.date", toDate );
    }
}
