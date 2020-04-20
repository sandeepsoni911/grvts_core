package com.owners.gravitas.business.builder.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.RequestSummaryBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.handler.NewStageChangeHandler;
import com.owners.gravitas.handler.OpportunityChangeHandler;
import com.owners.gravitas.handler.OpportunityChangeHandlerFactory;

/**
 * The Class AgentBuilderTest.
 *
 * @author vishwanathm
 */
public class AgentBuilderTest extends AbstractBaseMockitoTest {

    /** The agent builder. */
    @InjectMocks
    private AgentBuilder agentBuilder;

    /** The request summary builder. */
    @Mock
    private RequestSummaryBuilder requestSummaryBuilder;

    /** The OpportunityChangeHandlerFactory *. */
    @Mock
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /** The contact builder. */
    @Mock
    private ContactBuilder contactBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final Map< String, Object > source = new HashMap<>();
        final Map< String, String > contactDetails = new HashMap<>();
        contactDetails.put( "ContactId", "ContactId" );
        contactDetails.put( "FirstName", "FirstName" );
        contactDetails.put( "LastName", "LastName" );
        contactDetails.put( "Preferred_Contact_Method__c", "Preferred_Contact_Method__c" );
        contactDetails.put( "Preferred_Contact_Time__c", "Preferred_Contact_Time__c" );
        contactDetails.put( "Email", "Email@Email.com" );
        contactDetails.put( "Phone", "12212121212" );
        final Map< String, Object > opportunityDetails = new HashMap<>();
        opportunityDetails.put( "Owners_Agent__c", "Owners_Agent__c" );
        opportunityDetails.put( "Preferred_Language__c", "Preferred_Language__c" );
        opportunityDetails.put( "Lead_Request_Type__c", "Make An Offer" );
        opportunityDetails.put( "Gravitas_Record_History__c",
                "{\"requestType\":\"MAKE_OFFER\"}\n{\"requestType\":\"SCHEDULE_TOUR\"}" );

        final Map< String, Object > recordType = new HashMap<>();
        recordType.put( "Name", "Buyer" );
        opportunityDetails.put( "RecordType", recordType );
        source.put( "Contact", contactDetails );
        source.put( "Opportunity", opportunityDetails );
        List< Map< String, Object > > records = new ArrayList<>();
        records.add( ( Map< String, Object > ) ( Object ) source );
        Mockito.when( requestSummaryBuilder.convertTo( Mockito.any() ) ).thenReturn( "test" );

        agentBuilder.convertTo( records );

        opportunityDetails.put( "Lead_Request_Type__c", "Schedule a Tour" );
        AgentHolder agent = agentBuilder.convertTo( records );
        Assert.assertNotNull( agent );

        opportunityDetails.put( "Lead_Request_Type__c", "Request Information" );
        agent = agentBuilder.convertTo( records );
        Assert.assertNotNull( agent );

        opportunityDetails.put( "Gravitas_Record_History__c", "" );
        opportunityDetails.put( "Lead_Request_Type__c", "Make An Offer" );
        opportunityDetails.put( "Median_Price__c", "4545454" );
        contactDetails.put( "Email", null );
        contactDetails.put( "Phone", null );
        final OpportunityChangeHandler opportunityChangeHandler = new NewStageChangeHandler();
        ReflectionTestUtils.setField( opportunityChangeHandler, "taskTitle", "test" );
        ReflectionTestUtils.setField( agentBuilder, "enableOpportunityBuyerRequest", true );
        Mockito.when( opportunityChangeHandlerFactory.getChangeHandler( Mockito.any(), Mockito.any() ) )
                .thenReturn( opportunityChangeHandler );
        agent = agentBuilder.convertTo( records, new AgentHolder() );
        Assert.assertNotNull( agent );

        opportunityDetails.put( "Lead_Request_Type__c", "Schedule a Tour" );
        agent = agentBuilder.convertTo( records, new AgentHolder() );
        Assert.assertNotNull( agent );

        opportunityDetails.put( "Lead_Request_Type__c", "Request Information" );
        agent = agentBuilder.convertTo( records, new AgentHolder() );
        Assert.assertNotNull( agent );
    }

    /**
     * Test convert to null source.
     */
    @Test
    public void testConvertToNullSource() {
        AgentHolder agent = agentBuilder.convertTo( null );
        Assert.assertNull( agent );
    }

    /**
     * Test convert to null destination.
     */
    @Test
    public void testConvertToNullDestination() {
        AgentHolder agent = agentBuilder.convertTo( null, null );
        Assert.assertNotNull( agent );

        agent = agentBuilder.convertTo( null, new AgentHolder() );
        Assert.assertNotNull( agent );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        agentBuilder.convertFrom( new AgentHolder() );
    }

}
