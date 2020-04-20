package com.owners.gravitas.builder;

import static org.testng.Assert.assertNull;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.builder.CRMAgentBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetails;

/**
 * The Class CRMAgentBuilderTest.
 * 
 * @author ankusht
 */
public class CRMAgentBuilderTest extends AbstractBaseMockitoTest {

    /** The crm agent builder. */
    @InjectMocks
    private CRMAgentBuilder crmAgentBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenSourceIsNotNull() {
        final AgentSource asRequest = new AgentSource();
        asRequest.setDrivingRadius( "10" );
        AgentDetails request = crmAgentBuilder.convertTo( asRequest );
        Assert.assertNotNull( request );
        request = crmAgentBuilder.convertTo( asRequest, new AgentDetails() );
        Assert.assertNotNull( request );
    }

    /**
     * Test convert to should return null when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final AgentDetails response = crmAgentBuilder.convertTo( null );
        assertNull( response );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        crmAgentBuilder.convertFrom( new AgentDetails() );
    }

}
