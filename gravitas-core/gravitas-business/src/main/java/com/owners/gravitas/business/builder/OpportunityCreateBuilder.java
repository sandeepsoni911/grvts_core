package com.owners.gravitas.business.builder;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunityCreate;

/**
 * The Class OpportunitySourceBuilder.
 *
 * @author shivamm
 */
@Component( "opportunityCreateBuilder" )
public class OpportunityCreateBuilder extends AbstractBuilder< Map< String, Object >, OpportunityCreate > {

    /**
     * Converts Map< String, String > to OpportunitySource.
     *
     * @param source
     *            is source request.
     * @param destination
     *            the destination
     * @return the opportunity source
     */
    @Override
    public OpportunityCreate convertTo( Map< String, Object > source, OpportunityCreate destination ) {
        OpportunityCreate opportunityCreate = destination;
        if (source != null) {
            if (opportunityCreate == null) {
                opportunityCreate = new OpportunityCreate();
            }
            opportunityCreate.setOpportunityType( getRecordTypeName( source.get( "RecordType" ) ) );
        }
        return opportunityCreate;
    }

    /**
     * Gets the record type name.
     *
     * @param map
     *            the map
     * @return the record type name
     */
    private String getRecordTypeName( final Object map ) {
        return ( ( Map< String, String > ) map ).get( "Name" );
    }

    /** Method not supported. */
    @Override
    public Map< String, Object > convertFrom( OpportunityCreate source, Map< String, Object > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
