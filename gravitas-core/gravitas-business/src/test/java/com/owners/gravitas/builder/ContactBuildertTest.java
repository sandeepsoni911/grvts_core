package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.PhoneNumber;

/**
 * The Class ContactBuildertTest.
 *
 * @author raviz
 */
public class ContactBuildertTest extends AbstractBaseMockitoTest {

    /** The contact builder. */
    @InjectMocks
    private ContactBuilder contactBuilder;

    /**
     * Test convert to should return contact when source is not null and
     * destination is null.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "contactDomainDataProvider" )
    public void testConvertToShouldReturnContactWhenSourceIsNotNullAndDestinationIsNull( final Contact source ) {
        final com.owners.gravitas.dto.Contact destination = contactBuilder.convertTo( source );
        assertNotNull( destination );
        assertEquals( destination.getFirstName(), source.getFirstName() );
        assertEquals( destination.getLastName(), source.getLastName() );
        assertEquals( destination.getCrmId(), source.getCrmId() );
        assertEquals( destination.getDeleted(), source.getDeleted() );
        assertEquals( destination.getEmails(), source.getEmails() );
        assertEquals( destination.getLastModifiedBy(), source.getLastModifiedBy() );
        assertEquals( destination.getLastModifiedDtm(), source.getLastModifiedDtm() );
        assertEquals( destination.getPhoneNumbers(), source.getPhoneNumbers() );
        assertEquals( destination.getPreferredContactMethod(), source.getPreferredContactMethod() );
        assertEquals( destination.getPreferredContactTime(), source.getPreferredContactTime() );
    }

    /**
     * Test convert to should return contact when source is not null and
     * destination is not null.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "contactDomainDataProvider" )
    public void testConvertToShouldReturnContactWhenSourceIsNotNullAndDestinationIsNotNull( final Contact source ) {
        final com.owners.gravitas.dto.Contact destination = new com.owners.gravitas.dto.Contact();
        destination.setFirstName( "testFname" );
        destination.setLastName( "testLname" );
        final com.owners.gravitas.dto.Contact actualDestination = contactBuilder.convertTo( source, destination );
        assertNotNull( actualDestination );
        assertEquals( actualDestination.getFirstName(), source.getFirstName() );
        assertEquals( actualDestination.getLastName(), source.getLastName() );
        assertEquals( actualDestination.getCrmId(), source.getCrmId() );
        assertEquals( actualDestination.getDeleted(), source.getDeleted() );
        assertEquals( actualDestination.getEmails(), source.getEmails() );
        assertEquals( actualDestination.getLastModifiedBy(), source.getLastModifiedBy() );
        assertEquals( actualDestination.getLastModifiedDtm(), source.getLastModifiedDtm() );
        assertEquals( actualDestination.getPhoneNumbers(), source.getPhoneNumbers() );
        assertEquals( actualDestination.getPreferredContactMethod(), source.getPreferredContactMethod() );
        assertEquals( actualDestination.getPreferredContactTime(), source.getPreferredContactTime() );
    }

    /**
     * Test convert to should return null when source is null.
     */
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final com.owners.gravitas.dto.Contact destination = contactBuilder.convertTo( null );
        assertNull( destination );
    }

    /**
     * Test convert from should return contact when source is not null and
     * destination is null.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "contactDtoDataProvider" )
    public void testConvertFromShouldReturnContactWhenSourceIsNotNullAndDestinationIsNull(
            final com.owners.gravitas.dto.Contact source ) {
        final Contact destination = contactBuilder.convertFrom( source );
        assertNotNull( destination );
        assertEquals( destination.getFirstName(), source.getFirstName() );
        assertEquals( destination.getLastName(), source.getLastName() );
        assertEquals( destination.getCrmId(), source.getCrmId() );
        assertEquals( destination.getDeleted(), source.getDeleted() );
        assertEquals( destination.getEmails(), source.getEmails() );
        assertEquals( destination.getLastModifiedBy(), source.getLastModifiedBy() );
        assertEquals( destination.getLastModifiedDtm(), source.getLastModifiedDtm() );
        assertEquals( destination.getPhoneNumbers(), source.getPhoneNumbers() );
        assertEquals( destination.getPreferredContactMethod(), source.getPreferredContactMethod() );
        assertEquals( destination.getPreferredContactTime(), source.getPreferredContactTime() );
    }

    /**
     * Test convert from should return contact when source is not null and
     * destination is not null.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "contactDtoDataProvider" )
    public void testConvertFromShouldReturnContactWhenSourceIsNotNullAndDestinationIsNotNull(
            final com.owners.gravitas.dto.Contact source ) {
        final Contact destination = new Contact();
        destination.setFirstName( "testFname" );
        destination.setLastName( "testLname" );
        final Contact actualDestination = contactBuilder.convertFrom( source, destination );
        assertNotNull( actualDestination );
        assertEquals( actualDestination.getFirstName(), source.getFirstName() );
        assertEquals( actualDestination.getLastName(), source.getLastName() );
        assertEquals( actualDestination.getCrmId(), source.getCrmId() );
        assertEquals( actualDestination.getDeleted(), source.getDeleted() );
        assertEquals( actualDestination.getEmails(), source.getEmails() );
        assertEquals( actualDestination.getLastModifiedBy(), source.getLastModifiedBy() );
        assertEquals( actualDestination.getLastModifiedDtm(), source.getLastModifiedDtm() );
        assertEquals( actualDestination.getPhoneNumbers(), source.getPhoneNumbers() );
        assertEquals( actualDestination.getPreferredContactMethod(), source.getPreferredContactMethod() );
        assertEquals( actualDestination.getPreferredContactTime(), source.getPreferredContactTime() );
    }

    /**
     * Test convert from should return null when source is null.
     */
    public void testConvertFromShouldReturnNullWhenSourceIsNull() {
        final Contact destination = contactBuilder.convertFrom( null );
        assertNull( destination );
    }

    /**
     * Test convert to map should return map when source is not null and
     * destination is null.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "contactDtoDataProvider" )
    public void testConvertToMapShouldReturnMapWhenSourceIsNotNullAndDestinationIsNull(
            final com.owners.gravitas.dto.Contact source ) {
        final Map< String, Object > actualMap = contactBuilder.convertToMap( source, null );
        assertNotNull( actualMap );
        assertEquals( actualMap.get( "firstName" ), source.getFirstName() );
        assertEquals( actualMap.get( "lastName" ), source.getLastName() );
        assertEquals( actualMap.get( "crmId" ), source.getCrmId() );
        assertEquals( actualMap.get( "lastModifiedBy" ), source.getLastModifiedBy() );
        assertEquals( actualMap.get( "lastModifiedDtm" ), source.getLastModifiedDtm() );
        assertEquals( actualMap.get( "preferredContactMethod" ), source.getPreferredContactMethod() );
        assertEquals( actualMap.get( "preferredContactTime" ), source.getPreferredContactTime() );
        assertEquals( actualMap.get( "emails" ), source.getEmails() );
    }

    /**
     * Test convert to map should return map when source is not null and
     * destination is not null.
     *
     * @param source
     *            the source
     */
    @Test( dataProvider = "contactDtoDataProvider" )
    public void testConvertToMapShouldReturnMapWhenSourceIsNotNullAndDestinationIsNotNull(
            final com.owners.gravitas.dto.Contact source ) {
        final Contact destination = new Contact();
        destination.setFirstName( "testFname" );
        destination.setLastName( "testLname" );
        final Map< String, Object > actualMap = contactBuilder.convertToMap( source, destination );
        assertNotNull( actualMap );
        assertEquals( actualMap.get( "firstName" ), source.getFirstName() );
        assertEquals( actualMap.get( "lastName" ), source.getLastName() );
        assertEquals( actualMap.get( "crmId" ), source.getCrmId() );
        assertEquals( actualMap.get( "lastModifiedBy" ), source.getLastModifiedBy() );
        assertEquals( actualMap.get( "lastModifiedDtm" ), source.getLastModifiedDtm() );
        assertEquals( actualMap.get( "preferredContactMethod" ), source.getPreferredContactMethod() );
        assertEquals( actualMap.get( "preferredContactTime" ), source.getPreferredContactTime() );
        assertEquals( actualMap.get( "emails" ), source.getEmails() );
    }

    /**
     * Test convert to map should return empty map when source is null.
     */
    @Test
    public void testConvertToMapShouldReturnEmptyMapWhenSourceIsNull() {
        final Map< String, Object > actualMap = contactBuilder.convertToMap( null, null );
        assertNotNull( actualMap );
        assertEquals( actualMap.size(), 0 );
    }

    /**
     *
     * Gets the contact source domain.
     *
     * @return the contact source domain
     */
    @DataProvider( name = "contactDomainDataProvider" )
    private Object[][] getContactSourceDomain() {
        final Contact source = new Contact();
        final List< PhoneNumber > phoneNumbers = new ArrayList<>();
        phoneNumbers.add( new PhoneNumber() );
        final List< String > emails = new ArrayList<>();
        emails.add( "testEmail1" );
        emails.add( "testEmail1" );

        source.setFirstName( "testFirstName" );
        source.setLastName( "testLastName" );
        source.setCrmId( "testCrmId" );
        source.setDeleted( false );
        source.setEmails( emails );
        source.setPreferredContactMethod( "testPreferredContactMethod" );
        source.setPreferredContactTime( "testPreferredContactTime" );
        source.setLastModifiedBy( "testLastModifiedBy" );
        source.setLastModifiedDtm( 1L );
        source.setPhoneNumbers( phoneNumbers );
        return new Object[][] { { source } };
    }

    /**
     * Gets the contact source dto.
     *
     * @return the contact source dto
     */
    @DataProvider( name = "contactDtoDataProvider" )
    private Object[][] getContactSourceDto() {
        final com.owners.gravitas.dto.Contact source = new com.owners.gravitas.dto.Contact();
        final List< com.owners.gravitas.dto.PhoneNumber > phoneNumbers = new ArrayList<>();
        phoneNumbers.add( new com.owners.gravitas.dto.PhoneNumber() );
        final List< String > emails = new ArrayList<>();
        emails.add( "testEmail1" );
        emails.add( "testEmail1" );

        source.setFirstName( "testFirstName" );
        source.setLastName( "testLastName" );
        source.setCrmId( "testCrmId" );
        source.setDeleted( false );
        source.setEmails( emails );
        source.setPreferredContactMethod( "testPreferredContactMethod" );
        source.setPreferredContactTime( "testPreferredContactTime" );
        source.setLastModifiedBy( "testLastModifiedBy" );
        source.setLastModifiedDtm( 1L );
        source.setPhoneNumbers( phoneNumbers );
        return new Object[][] { { source } };
    }
}
