package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.request.CoShoppingLeadUpdateModel;
import com.owners.gravitas.dto.request.CoShoppingLeadUpdateRequest;

/**
 * 
 * @author imranmoh
 *
 */
@Component
public class CoShoppingLeadUpdateModelBuilder {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CoShoppingLeadUpdateModelBuilder.class );

    public CoShoppingLeadUpdateModel build( final String coShoppingId, final String propertyTourInformation,
            final String status ) {
        LOGGER.info( "started building CoShoppingLeadUpdateModel with id {}, property tour information {}, status {} ",
                coShoppingId, propertyTourInformation, status );
        final CoShoppingLeadUpdateModel request = new CoShoppingLeadUpdateModel();
        request.setId( coShoppingId );
        request.setPropertyTourInformation( propertyTourInformation );
        request.setStatus( status );
        return request;

    }

    /**
     * Method to get lead update request List for patch update
     * 
     * @param coShoppingLeadUpdateModel
     * @return
     */
    public CoShoppingLeadUpdateRequest buildCoShoppingLeadUpdateRequest(
            final CoShoppingLeadUpdateModel coShoppingLeadUpdateModel ) {
        final CoShoppingLeadUpdateRequest leadUpdateRequest = new CoShoppingLeadUpdateRequest();
        final List< CoShoppingLeadUpdateModel > leadUpdateRequestList = new ArrayList<>();
        leadUpdateRequestList.add( coShoppingLeadUpdateModel );
        leadUpdateRequest.setLeadUpdateRequest( leadUpdateRequestList );
        return leadUpdateRequest;
    }
}
