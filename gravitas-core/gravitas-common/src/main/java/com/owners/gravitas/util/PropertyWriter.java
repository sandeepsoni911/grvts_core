package com.owners.gravitas.util;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.owners.gravitas.annotation.JmxChange;
import com.owners.gravitas.constants.Constants;

/**
 * The Class PropertyWriter provide methods to manage/update external
 * properties at runtime.
 */
@Component
public class PropertyWriter {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PropertyWriter.class );

    /** The builder. */
    private FileBasedConfigurationBuilder< FileBasedConfiguration > builder;

    /** The auditor. */
    @Autowired
    private AuditorAware< String > auditor;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        final Parameters params = new Parameters();
        builder = new FileBasedConfigurationBuilder< FileBasedConfiguration >( PropertiesConfiguration.class )
                .configure( params.properties()
                        .setFileName( Constants.GRAVITAS_CONFIG_DIR + File.separatorChar + "gravitas.properties" ) );
    }

    /**
     * Save jmx property.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    @JmxChange
    public void saveJmxProperty( final String key, final Object value ) {
        try {
            final PropertiesConfiguration config = ( PropertiesConfiguration ) builder.getConfiguration();

            config.getLayout().setComment( key,
                    "=== JMX updated attribute by user: " + auditor.getCurrentAuditor() + " at: " + DateTime.now() );
            config.getLayout().setSeparator( key, "=" );
            config.setProperty( key, value );
            builder.save();
        } catch ( final Exception e ) {
            // Purposely suppressed error as don't want to break the execution
            // because of side feature of updating property file
            LOGGER.error( "Error updating property:" + e.getLocalizedMessage(), e );
        }
    }
}
