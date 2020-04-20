package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.request.OCLGenericLeadRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The test class OwnerGenericLeadRequestBuilderTest.
 *
 * @author raviz
 */
public class OwnerGenericLeadRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The owner generic lead request builder. */
    @InjectMocks
    private OCLGenericLeadRequestBuilder ownerGenericLeadRequestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final LeadSource leadSource = getLeadSource();
        leadSource.setListingCreationDate( new DateTime() );
        leadSource.setLastVisitDateTime( new DateTime() );

        final GenericLeadRequest destination = new GenericLeadRequest();
        final GenericLeadRequest actualLeadRequest = ownerGenericLeadRequestBuilder.convertTo( leadSource,
                destination );

        assertNotNull( actualLeadRequest );
        assertEquals( actualLeadRequest, destination );
        assertEquals( leadSource.getFirstName(), actualLeadRequest.getFirstName() );
        assertEquals( leadSource.getLastName(), actualLeadRequest.getLastName() );
        assertEquals( leadSource.getEmail(), actualLeadRequest.getEmail() );
    }

    /**
     * Test convert to when lead request is null.
     */
    @Test
    public void testConvertTo_whenLeadRequestIsNull() {
        final LeadSource leadSource = getLeadSource();
        final GenericLeadRequest destination = null;
        final GenericLeadRequest actualLeadRequest = ownerGenericLeadRequestBuilder.convertTo( leadSource,
                destination );

        assertNotNull( actualLeadRequest );
        assertNotEquals( actualLeadRequest, destination );
        assertEquals( leadSource.getFirstName(), actualLeadRequest.getFirstName() );
        assertEquals( leadSource.getLastName(), actualLeadRequest.getLastName() );
        assertEquals( leadSource.getEmail(), actualLeadRequest.getEmail() );
    }

    /**
     * Test convert from.
     */
    @Test
    public void testConvertFrom() {
        final GenericLeadRequest request = new GenericLeadRequest();
        final LeadSource leadSource = new LeadSource();
        assertNotNull(ownerGenericLeadRequestBuilder.convertFrom( request, leadSource ));
    }

    /**
     * Gets lead source.
     *
     * @return {@link LeadSource}
     */
    private LeadSource getLeadSource() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setFirstName( "firstName" );
        leadSource.setLastName( "lastName" );
        leadSource.setEmail( "test@gmail.com" );
        return leadSource;
    }
}
