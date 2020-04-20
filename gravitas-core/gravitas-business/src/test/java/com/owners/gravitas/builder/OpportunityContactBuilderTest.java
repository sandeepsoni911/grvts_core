package com.owners.gravitas.builder;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.business.builder.OpportunityContactBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class OpportunityContactBuilderTest.
 *
 * @author vishwanathm
 */
public class OpportunityContactBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity contact builder. */
    @InjectMocks
    private OpportunityContactBuilder opportunityContactBuilder;

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
        final Map< String, String > opportunityDetails = new HashMap<>();
        opportunityDetails.put( "Owners_Agent__c", "Owners_Agent__c" );
        opportunityDetails.put( "Preferred_Language__c", "Preferred_Language__c" );
        source.put( "Contact", contactDetails );
        source.put( "Opportunity", opportunityDetails );

        final OpportunityContact contact = opportunityContactBuilder.convertTo( ( Map< String, Object > ) source );
        Assert.assertNotNull( contact );
        Assert.assertNotNull( contact.getPrimaryContact() );
        Assert.assertEquals( contact.getPrimaryContact().getFirstName(), "FirstName" );
    }

    /**
     * Test convert to conditions.
     */
    @Test
    public void testConvertToConditions() {
        final OpportunityContact contact1 = opportunityContactBuilder.convertTo( null );
        Assert.assertNull( contact1 );

        final Map< String, Object > source = new HashMap<>();
        final Map< String, String > contactDetails = new HashMap<>();
        contactDetails.put( "ContactId", "ContactId" );
        contactDetails.put( "FirstName", "FirstName" );
        contactDetails.put( "LastName", "LastName" );
        contactDetails.put( "Preferred_Contact_Method__c", "Preferred_Contact_Method__c" );
        contactDetails.put( "Preferred_Contact_Time__c", "Preferred_Contact_Time__c" );
        final Map< String, String > opportunityDetails = new HashMap<>();
        opportunityDetails.put( "Owners_Agent__c", "Owners_Agent__c" );
        opportunityDetails.put( "Preferred_Language__c", "Preferred_Language__c" );
        source.put( "Contact", contactDetails );
        source.put( "Opportunity", opportunityDetails );

        final OpportunityContact contact = opportunityContactBuilder.convertTo( ( Map< String, Object > ) source,
                new OpportunityContact() );
        Assert.assertNotNull( contact );
        Assert.assertNotNull( contact.getPrimaryContact() );
        Assert.assertEquals( contact.getPrimaryContact().getFirstName(), "FirstName" );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        opportunityContactBuilder.convertFrom( new OpportunityContact() );
    }

    /**
     * Test convert to should return same object for null source.
     */
    @Test
    public void testConvertTo_shouldReturnSameObjectForNullSource() {
        final OpportunityContact contact1 = opportunityContactBuilder.convertTo( null );
        Assert.assertNull( contact1 );

        final Map< String, Object > source = new HashMap<>();
        final Map< String, String > contactDetails = new HashMap<>();
        contactDetails.put( "ContactId", "ContactId" );
        contactDetails.put( "FirstName", "FirstName" );
        contactDetails.put( "LastName", "LastName" );
        contactDetails.put( "Preferred_Contact_Method__c", "Preferred_Contact_Method__c" );
        contactDetails.put( "Preferred_Contact_Time__c", "Preferred_Contact_Time__c" );
        final Map< String, String > opportunityDetails = new HashMap<>();
        opportunityDetails.put( "Owners_Agent__c", "Owners_Agent__c" );
        opportunityDetails.put( "Preferred_Language__c", "Preferred_Language__c" );
        source.put( "Contact", contactDetails );
        source.put( "Opportunity", opportunityDetails );

        final OpportunityContact contact = opportunityContactBuilder.convertTo( ( Map< String, Object > ) source,
                new OpportunityContact() );
        Assert.assertNotNull( contact );
        Assert.assertNotNull( contact.getPrimaryContact() );
        Assert.assertEquals( contact.getPrimaryContact().getFirstName(), "FirstName" );
    }
}
