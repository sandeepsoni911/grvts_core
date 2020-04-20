package com.owners.gravitas.validator;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.exception.InvalidArgumentException;

/**
 * The Class ContactUpdateRequestValidatorTest.
 *
 * @author amits
 */
public class ContactUpdateRequestValidatorTest extends AbstractBaseMockitoTest {

    /** The contact update request validator. */
    @InjectMocks
    ContactUpdateRequestValidator contactUpdateRequestValidator;

    /**
     * Test validate contact request.
     */
    @Test
    public void testValidateContactRequest() {
        Map< String, String > request = new HashMap< String, String >();
        request.put( "lastName", "test" );
        contactUpdateRequestValidator.validate( request );
        Assert.assertNotEquals( request, null );
    }

    /**
     * Test validate opportunity request throws exception.
     */
    @Test( expectedExceptions = InvalidArgumentException.class )
    public void testValidateOpportunityRequestThrowsException() {
        Map< String, String > request = new HashMap< String, String >();
        request.put( "phone", "asd" );
        contactUpdateRequestValidator.validate( request );
        Assert.assertNotEquals( request, null );
    }

}
