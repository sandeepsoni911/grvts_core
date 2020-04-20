package com.owners.gravitas.validator;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.exception.InvalidArgumentException;

/**
 * The Class OpportunityUpdateRequestValidatorTest.
 *
 * @author amits
 */
public class OpportunityUpdateRequestValidatorTest extends AbstractBaseMockitoTest {

    /** The opportunity update request validator. */
    @InjectMocks
    OpportunityUpdateRequestValidator opportunityUpdateRequestValidator;

    /**
     * Test validate opportunity request.
     */
    @Test
    public void testValidateOpportunityRequest() {
        Map< String, Object > request = new HashMap< String, Object >();
        opportunityUpdateRequestValidator.validateOpportunityRequest( request );
        Assert.assertNotEquals( request, null );
    }

    /**
     * Test validate opportunity request throws exception.
     */
    @Test( expectedExceptions = InvalidArgumentException.class )
    public void testValidateOpportunityRequestThrowsException() {
        Map< String, Object > request = new HashMap< String, Object >();
        request.put( "salesPrice", "asd" );
        opportunityUpdateRequestValidator.validateOpportunityRequest( request );
        Assert.assertNotEquals( request, null );
    }

    /**
     * Test validate opportunity request with date values.
     */
    @Test
    public void testValidateOpportunityRequestWithDateValues() {
        Map< String, Object > request = new HashMap< String, Object >();
        request.put( "pendingDate", "2016-06-24 12:30 AM" );
        request.put( "expectedContractDate", "2016-06-24 12:30 AM" );
        request.put( "actualClosingDate", "2016-06-24 12:30 AM" );
        opportunityUpdateRequestValidator.validateOpportunityRequest( request );
        Assert.assertNotEquals( request, null );
    }

}
