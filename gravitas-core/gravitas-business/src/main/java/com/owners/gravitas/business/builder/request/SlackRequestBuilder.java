package com.owners.gravitas.business.builder.request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.Property;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.dto.request.SlackRequest;

/**
 * The Class SlackRequestBuilder.
 */
@Component( "slackRequestBuilder" )
public class SlackRequestBuilder extends AbstractBuilder< OpportunityRequest, SlackRequest > {

    /** The message. */
    @Value( value = "${slack.channel.message}" )
    private String message;

    @Value( value = "${slack.bot.name}" )
    private String botName;

    /**
     * Converts the {@link OpportunityRequest} to {@link SlackRequest}
     *
     */
    @Override
    public SlackRequest convertTo( final OpportunityRequest source, final SlackRequest destination ) {
        SlackRequest slackRequest = destination;
        if (source != null) {
            if (slackRequest == null) {
                slackRequest = new SlackRequest();
            }
            slackRequest.setText( getSlackMessage( source ) );
            slackRequest.setUsername( botName );
        }
        return slackRequest;
    }

    /**
     * Creates the formatted message for slack.
     *
     * @param source
     *            is the source of place holders.
     * @return formatted message.
     */
    private String getSlackMessage( final OpportunityRequest source ) {
        final Property property = source.getPropertyOrder().getProperty();
        final SellerAddress address = property.getAddress();

        return String.format( message, getPropertyOwner( source.getSeller() ), address.getCity(), address.getState(),
                property.getListingId(), source.getPropertyOrder().getOrderType() );
    }

    /**
     * Gets the property owner.
     *
     * @param seller
     *            the seller
     * @return the property owner
     */
    private String getPropertyOwner( final Seller seller ) {
        String owner = seller.getLastName();
        if (StringUtils.isNotBlank( seller.getFirstName() )) {
            owner = seller.getFirstName() + " " + owner;
        }
        return owner;
    }

    /** Method not supported. */
    @Override
    public OpportunityRequest convertFrom( SlackRequest source, OpportunityRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
