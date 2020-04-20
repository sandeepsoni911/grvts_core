package com.owners.gravitas.business.builder;

import java.util.Date;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.util.AddressFormatter;
import com.zuner.coshopping.model.enums.LeadRequestType;
import com.zuner.coshopping.model.lead.LeadModel;

// TODO: Auto-generated Javadoc
/**
 * The Class CoShoppingLeadBuilder.
 *
 * @author gururasm
 */
@Component( "coShoppingLeadBuilder" )
public class CoShoppingLeadBuilder extends AbstractBuilder< LeadRequest, CoShoppingLeadRequest > {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CoShoppingLeadBuilder.class );

    /** The address formatter. */
    @Autowired
    private AddressFormatter addressFormatter;
    
    @Autowired
    private CoShoppingService coShoppingService;

    /**
     * Builds the.
     *
     * @param opportunityId
     *            the opportunity id
     * @param listingId
     *            the listing id
     * @param tourTime
     *            the tour time
     * @param timeZone
     *            the timeZone
     * 
     * @return the co shopping lead request
     */
    public CoShoppingLeadRequest build( final Date tourTime, final Contact contact,
            final PropertyData propertyDetails, final String timeZone) {
        LOGGER.info( "building request started" );
        final CoShoppingLeadRequest request = new CoShoppingLeadRequest();
        LeadModel model = new LeadModel();
        model = setBuyerDetails( model, contact );
        model = setPropertyDetails( tourTime, model, propertyDetails, timeZone );
        request.setLeadModel( model );
        LOGGER.info( "building request completed" );
        return request;
    }

    /**
     * Sets the buyer details.
     *
     * @param opportunityId
     *            the new buyer details
     */
    private LeadModel setBuyerDetails( final LeadModel model, final Contact contact ) {
        if (contact != null) {
            model.setEmail( contact.getEmail() );
            model.setFirstName( contact.getFirstName() );
            model.setLastName( contact.getLastName() );
            model.setUserId( contact.getOwnersComId() );
        }
        return model;
    }

    /**
     * Sets the property details.
     *
     * @param listingId
     *            the new property details
     * @param tourTime
     *            the tour time
     * @param timeZone
     *            the timeZone
     *
     */
    private LeadModel setPropertyDetails( final Date tourTime, final LeadModel model,
            final PropertyData propertyAddress, final String timeZone ) {
        model.setPropertyBedroom( propertyAddress.getBedRooms() );
        model.setPropertyBathroom( propertyAddress.getBathRooms().toString() );
        model.setPropertyPrice( propertyAddress.getPrice() );
        model.setPropertyAddress( addressFormatter.formatAddress( propertyAddress.getPropertyAddress() ) );
        if (null != tourTime) {
            model.setPropertyTourInformation( coShoppingService.getTourTime( tourTime, timeZone ) );
        }
        model.setMlsId( propertyAddress.getMlsID() );
        if (CollectionUtils.isNotEmpty( propertyAddress.getImages() )) {
            final String firstImageUrl = propertyAddress.getImages().get( 0 ).getThumbnailImageURL();
            LOGGER.info( "first image url is : {}", firstImageUrl );
            model.setPropertyImageURL( firstImageUrl );
        }
        // model.setOfferAmount("TBD");
        // model.setQuestion( "TBD" );
        // model.setPropertyHalfBathroom( "TBD" );
        return model;
    }


    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public CoShoppingLeadRequest convertTo( final LeadRequest source, final CoShoppingLeadRequest destination ) {
        CoShoppingLeadRequest coShoppingLeadRequest = destination;
        LeadModel leadModel = null;
        if (null != source) {
            if (null == coShoppingLeadRequest) {
                coShoppingLeadRequest = new CoShoppingLeadRequest();
            }
            if (null == coShoppingLeadRequest.getLeadModel()) {
                leadModel = new LeadModel();
                coShoppingLeadRequest.setLeadModel( leadModel );
            } else {
                leadModel = coShoppingLeadRequest.getLeadModel();
            }

            BeanUtils.copyProperties( source, leadModel, "propertyTourInformation", "question", "offerAmount" );
            leadModel.setUserId( source.getOwnersComIdentifier() );
            if (LeadRequestType.SCHEDULE_TOUR.name().equalsIgnoreCase( source.getRequestType() )) {
                leadModel.setType( LeadRequestType.SCHEDULE_TOUR );
                leadModel.setPropertyTourInformation( source.getPropertyTourInformation() );
            }
            if (LeadRequestType.REQUEST_INFORMATION.name().equalsIgnoreCase( source.getRequestType() )) {
                leadModel.setType( LeadRequestType.REQUEST_INFORMATION );
                leadModel.setQuestion( source.getQuestion() );
            }
            if (LeadRequestType.MAKE_OFFER.name().equalsIgnoreCase( source.getRequestType() )) {
                leadModel.setType( LeadRequestType.MAKE_OFFER );
                leadModel.setOfferAmount( source.getOfferAmount() );
            }
        }
        return coShoppingLeadRequest;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public LeadRequest convertFrom( final CoShoppingLeadRequest source, final LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertTo is not supported" );
    }
}
