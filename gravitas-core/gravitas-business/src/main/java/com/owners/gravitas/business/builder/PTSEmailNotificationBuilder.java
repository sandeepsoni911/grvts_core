package com.owners.gravitas.business.builder;

import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;

/**
 * The Class PTSEmailNotificationBuilder.
 * 
 * @author ankusht
 */
public class PTSEmailNotificationBuilder extends AgentEmailNotificationBuilder {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PTSEmailNotificationBuilder.class );

    /** The from email. */
    @Value( "${pts.notification.from.email}" )
    private String ptsFromEmail;

    /**
     * Convert to.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the email notification
     */
    public EmailNotification convertTo( final AgentSource source, final EmailNotification destination ) {
        final Map< String, Object > sourceMap = new HashMap<>();
        sourceMap.put( FROM_EMAIL, ptsFromEmail );
        sourceMap.put( NAME, source.getName() );
        sourceMap.put( TO_EMAIL, source.getEmail() );
        return convertTo( sourceMap, destination );
    }

    /**
     * Validate state.
     *
     * @param opportunitySource
     *            the opportunity source
     * @return true, if successful
     */
    protected boolean validateState( final OpportunitySource opportunitySource ) {
        boolean isValidState = false;
        if (opportunitySource.getOpportunityType().equals( BUYER.getType() )) {
            isValidState = isNotBlank( opportunitySource.getPropertyState() );
        } else if (opportunitySource.getOpportunityType().equals( SELLER.getType() )) {
            isValidState = isNotBlank( opportunitySource.getSellerPropertyState() );
        }
        LOGGER.info( "Validated state: " + isValidState );
        return isValidState;
    }
}
