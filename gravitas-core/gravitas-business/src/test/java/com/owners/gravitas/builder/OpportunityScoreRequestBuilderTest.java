package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.request.OpportunityScoreRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class OpportunityScoreRequestBuilderTest.
 *
 * @author shivamm
 */
public class OpportunityScoreRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity score request builder. */
    @InjectMocks
    private OpportunityScoreRequestBuilder opportunityScoreRequestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        OpportunitySource source = new OpportunitySource();
        source.setRecordType( "test" );
        source.setLeadSource( "test" );
        source.setBuyerReadinessTimeline( "test" );
        source.setPropertyState( "test" );
        source.setPreApprovedForMortgage( "test" );
        source.setLeadRequestType( "test" );
        source.setWorkingWithExternalAgent( "test" );
        source.setBuyerLeadQuality( "test" );
        source.setDedupCount( "test" );
        Map< String, String > destination = opportunityScoreRequestBuilder.convertTo( source );
        Assert.assertNotNull( destination );
        assertEquals( source.getRecordType(), destination.get( "leadRecordType" ) );
    }

    /**
     * Test convert to source as null.
     */
    @Test
    public void testConvertToSourceAsNull() {
        Map< String, String > destination = opportunityScoreRequestBuilder.convertTo( new OpportunitySource() );
        Assert.assertNotNull( destination );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        opportunityScoreRequestBuilder.convertFrom( new HashMap< String, String >() );
    }
}
