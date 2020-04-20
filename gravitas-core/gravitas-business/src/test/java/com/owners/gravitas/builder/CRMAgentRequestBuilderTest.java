package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.CRMAgentRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.UserAddress;
import com.owners.gravitas.dto.crm.request.CRMAgentRequest;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.service.RecordTypeService;

/**
 * The Class CRMAccountRequestBuilderTest.
 *
 * @author amits
 */
public class CRMAgentRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The crm account request builder. */
    @InjectMocks
    private CRMAgentRequestBuilder crmAgentRequestBuilder;
    
    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {

        final AgentOnboardRequest aoRequest = new AgentOnboardRequest();
        aoRequest.setAddress( new UserAddress() );
        aoRequest.setStatus( "test" );
        CRMAgentRequest request = crmAgentRequestBuilder.convertTo( aoRequest );
        Assert.assertNotNull( request );

        request = crmAgentRequestBuilder.convertTo( aoRequest, new CRMAgentRequest() );
        Assert.assertNotNull( request );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        crmAgentRequestBuilder.convertFrom( new CRMAgentRequest() );
    }

}
