package com.owners.gravitas.domain.entity;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ListingIdDetailsTest.
 *
 * @author pabhishek
 */
public class ListingIdDetailsTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The listing id details values. */
    private PropertiesAndValues listingIdDetailsValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createListingIdDetailsValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "listingId", null );
        defaultValues.put( "opportunity", null );
    }

    /**
     * Creates the listing id details values.
     */
    private void createListingIdDetailsValues() {
        final Opportunity opportunity = new Opportunity();
        listingIdDetailsValues = new PropertiesAndValues();
        listingIdDetailsValues.put( "id", "test" );
        listingIdDetailsValues.put( "createdBy", "test" );
        listingIdDetailsValues.put( "createdDate", new DateTime() );
        listingIdDetailsValues.put( "lastModifiedBy", "test" );
        listingIdDetailsValues.put( "lastModifiedDate", new DateTime() );
        listingIdDetailsValues.put( "listingId", "listingId" );
        listingIdDetailsValues.put( "opportunity", opportunity );
    }

    /**
     * Gets the listing id for has code.
     *
     * @return the listing id for has code
     */
    private ListingIdDetails getListingIdForHasCode() {
        ListingIdDetails testHash = new ListingIdDetails();
        testHash.setId( "id" );
        testHash.setCreatedBy( "createdBy" );
        //testHash.setOpportunity( new OpportunityV1() );
        testHash.setLastModifiedBy( "lastModifiedBy" );
        testHash.setListingId( "listingId" );
        testHash.setCreatedDate( new DateTime() );
        testHash.setLastModifiedDate( new DateTime() );
        return testHash;
    }

    /**
     * Test listing id details.
     */
    @Test
    public final void testListingIdDetails() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( ListingIdDetails.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, listingIdDetailsValues );
    }

    /**
     * Test equals and hash code.
     */
    @Test
    public void testEqualsAndHashCode() {
        final ListingIdDetails testHash1 = getListingIdForHasCode();
        final ListingIdDetails testHash2 = new ListingIdDetails();
        testHash2.setListingId( "listingId" );
        assertEquals( testHash1, testHash2 );
        assertEquals( testHash1.hashCode(), testHash2.hashCode() );
    }

    @Test
    public void testHashcodeWithListingIdValueNull() {
        final ListingIdDetails testHash1 = getListingIdForHasCode();
        testHash1.setListingId( null );
        final ListingIdDetails testHash2 = new ListingIdDetails();
        testHash2.setListingId(null);
        assertEquals( testHash1.hashCode(), testHash2.hashCode() );
    }
}
