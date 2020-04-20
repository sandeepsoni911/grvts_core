package com.owners.gravitas.service.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;

/**
 * The Class CRMAgentResponseBuilderTest.
 *
 * @author raviz
 */
public class CRMAgentResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The crm agent response builder. */
    @InjectMocks
    private CRMAgentResponseBuilder crmAgentResponseBuilder;

    /**
     * Test convert to should return response when source is not null.
     */
    @Test( dataProvider = "sourceData" )
    public void testConvertToShouldReturnResponseWhenSourceIsNotNull( final Map< String, Object > source ) {
        final CRMAgentResponse agentResponse = crmAgentResponseBuilder.convertTo( source );
        assertNotNull( agentResponse );
        assertEquals( source.get( "Id" ), agentResponse.getId() );
    }

    /**
     * Test convert to should return response when source is not null and
     * destination is provided.
     */
    @Test( dataProvider = "sourceData" )
    public void testConvertToShouldReturnResponseWhenSourceIsNotNullAndDestinationIsProvided(
            final Map< String, Object > source ) {
        final CRMAgentResponse destination = new CRMAgentResponse();
        final String address1 = "address1";
        destination.setAddress1( address1 );
        final CRMAgentResponse agentResponse = crmAgentResponseBuilder.convertTo( source, destination );
        assertNotNull( agentResponse );
        assertEquals( agentResponse.getId(), source.get( "Id" ) );
        assertEquals( agentResponse.getAddress1(), address1 );
    }

    /**
     * Test convert to should return null when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final CRMAgentResponse agentResponse = crmAgentResponseBuilder.convertTo( null );
        assertNull( agentResponse );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        crmAgentResponseBuilder.convertFrom( new CRMAgentResponse() );
    }

    /**
     * Gets the source data.
     *
     * @return the source data
     */
    @DataProvider( name = "sourceData" )
    public Object[][] getSourceData() {
        final Map< String, Object > source = new HashMap<>();
        source.put( "Id", "id" );
        source.put( "Name", "Name" );
        source.put( "Email__c", "email" );
        source.put( "Address1__c", "address1" );
        source.put( "Address2__c", "address2" );
        return new Object[][] { { source } };
    }
}
