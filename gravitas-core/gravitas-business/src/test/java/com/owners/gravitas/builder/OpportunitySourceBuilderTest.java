package com.owners.gravitas.builder;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.OpportunitySourceBuilder;
import com.owners.gravitas.business.builder.RequestSummaryBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class OpportunitySourceBuilderTest.
 *
 * @author vishwanathm
 */
public class OpportunitySourceBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity source builder. */
    @InjectMocks
    private OpportunitySourceBuilder opportunitySourceBuilder;

    /** The request summary builder. */
    @Mock
    private RequestSummaryBuilder requestSummaryBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final Map< String, Object > source = getSource();
        OpportunitySource opportunitySource = opportunitySourceBuilder
                .convertTo( ( Map< String, Object > ) source );
        Assert.assertNotNull( opportunitySource );

        final Map< String, Object > source1 = new HashMap<>();
        source1.put( "LeadSource", "LeadSource" );
        final Map< String, Object > recordType = new HashMap<>();
        recordType.put( "Name", "Buyer" );
        source1.put( "RecordType", recordType );
        source1.put( "Median_Price__c", "4545454.00" );
        source1.put( "Commission_Post_Closing__c", "Commission_Post_Closing__c" );

        opportunitySource = opportunitySourceBuilder.convertTo( ( Map< String, Object > )source );
        Assert.assertNotNull( opportunitySource );
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    protected Map< String, Object > getSource() {
        Map< String, Object > source = new HashMap<>();
        final Map< String, Object > recordType = new HashMap<>();
        recordType.put( "Name", "Buyer" );
        source.put( "RecordType", recordType );
        source.put( "LeadSource", "LeadSource" );
        source.put( "Median_Price__c", "4545454.00" );
        source.put( "Commission_Post_Closing__c", "Commission_Post_Closing__c" );
        source.put( "Gravitas_Record_History__c",
                "{\"requestType\":\"MAKE_OFFER\"}\n{\"requestType\":\"SCHEDULE_TOUR\",\"propertyTourInformation\":\"propertyTourInformation\"}" );
        return source;
    }

    /**
     * Test convert to null destination.
     */
    @Test
    public void testConvertToNullDestination() {
        Mockito.when( requestSummaryBuilder.convertTo( new String[] {} ) ).thenReturn( "test" );
        final Map< String, Object > source = getSource();
        OpportunitySource source1 = opportunitySourceBuilder.convertTo( ( Map< String, Object > ) source, null );
        Assert.assertNotNull( source1 );

        source1 = opportunitySourceBuilder.convertTo( null, null );
        Assert.assertNull( source1 );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        OpportunitySource source = opportunitySourceBuilder.convertTo( null );
        Assert.assertNull( source );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        opportunitySourceBuilder.convertFrom( new OpportunitySource() );
    }

}
