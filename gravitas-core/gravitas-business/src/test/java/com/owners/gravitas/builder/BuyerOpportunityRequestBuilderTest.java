package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.BuyerOpportunityRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class CRMLeadRequestBuilderTest.
 *
 * @author vishwanathm
 */
public class BuyerOpportunityRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The crm lead request builder. */
    @InjectMocks
    private BuyerOpportunityRequestBuilder buyerOpportunityRequestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        CRMOpportunityRequest leadRequest = buyerOpportunityRequestBuilder.convertTo( new GenericLeadRequest() );
        Assert.assertNotNull( leadRequest );

        leadRequest = buyerOpportunityRequestBuilder.convertTo( new GenericLeadRequest(), new CRMOpportunityRequest() );
        Assert.assertNotNull( leadRequest );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        final CRMOpportunityRequest leadRequest = buyerOpportunityRequestBuilder.convertTo( null );
        Assert.assertNull( leadRequest );
    }

    /**
     * Test convert to destination null.
     */
    @Test
    public void testConvertToDestinationNull() {
        CRMOpportunityRequest leadRequest = buyerOpportunityRequestBuilder.convertTo( new GenericLeadRequest(), null );
        Assert.assertNotNull( leadRequest );

        leadRequest = buyerOpportunityRequestBuilder.convertTo( null, null );
        Assert.assertNull( leadRequest );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        buyerOpportunityRequestBuilder.convertFrom( new CRMOpportunityRequest() );
    }

}
