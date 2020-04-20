package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.AlertDetails;
import com.owners.gravitas.domain.entity.ActivityProperty;
import com.owners.gravitas.domain.entity.ContactActivity;

/**
 * The Class ContactActivityBuilder.
 * 
 * @author pabhishek
 */
@Component
public class ContactActivityBuilder extends AbstractBuilder< AlertDetails, ContactActivity > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public ContactActivity convertTo( final AlertDetails source, final ContactActivity destination ) {
        ContactActivity contactActivity = destination;
        if (source != null) {
            if (contactActivity == null) {
                contactActivity = new ContactActivity();
            }
            contactActivity.setActivityProperties( buildActivityPropertyList( source ) );
        }
        return contactActivity;
    }

    /**
     * Builds the activity property list.
     *
     * @param alertDetails
     *            the alert details
     * @return the list
     */
    private List< ActivityProperty > buildActivityPropertyList( final AlertDetails alertDetails ) {
        final List< ActivityProperty > activityProperties = new ArrayList< ActivityProperty >();
        if (CollectionUtils.isNotEmpty( alertDetails.getClientEventDetails() )) {
            alertDetails.getClientEventDetails().forEach( alert -> {
                final ActivityProperty activityProperty = new ActivityProperty();
                activityProperty.setListingId( alert.getListingId() );
                activityProperty.setPropertyId( alert.getPropertyId() );
                activityProperties.add( activityProperty );
            } );
        }
        return activityProperties;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AlertDetails convertFrom( final ContactActivity source, final AlertDetails destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
