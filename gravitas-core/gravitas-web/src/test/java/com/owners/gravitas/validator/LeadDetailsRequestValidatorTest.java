package com.owners.gravitas.validator;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadDetailsRequest;
import com.owners.gravitas.exception.InvalidArgumentException;

/**
 * 
 * The class LeadDetailsRequestValidator
 * 
 * @author imranmoh
 *
 */
public class LeadDetailsRequestValidatorTest extends AbstractBaseMockitoTest {

    @InjectMocks
    LeadDetailsRequestValidator requestValidator;

    /**
     * Test validate Lead Details request.
     */
    @Test
    public void testValidateLeadDetailsRequest() {
        final LeadDetailsRequest request = new LeadDetailsRequest();
        request.setDirection( "asc" );
        request.setPage( 0 );
        request.setSize( 10 );
        request.setProperty( "score" );
        requestValidator.validateLeadDetailsRequest( request );
        Assert.assertNotEquals( request, null );
        request.setProperty( "state" );
        requestValidator.validateLeadDetailsRequest( request );
        request.setProperty( "leadName" );
        requestValidator.validateLeadDetailsRequest( request );
        request.setProperty( "email" );
        requestValidator.validateLeadDetailsRequest( request );
        request.setProperty( "phone" );
        requestValidator.validateLeadDetailsRequest( request );
        request.setProperty( "createdDate" );
        requestValidator.validateLeadDetailsRequest( request );
        request.setProperty( "crmId" );
        requestValidator.validateLeadDetailsRequest( request );
    }

    /**
     * Test validate Lead Details request throws exception.
     */
    @Test( expectedExceptions = InvalidArgumentException.class )
    public void testValidateLeadDetailsRequestThrowsException() {
        final LeadDetailsRequest request = new LeadDetailsRequest();
        request.setDirection( "des" );
        request.setPage( -1 );
        request.setProperty( "abc" );
        request.setSize( -1 );
        requestValidator.validateLeadDetailsRequest( request );
        Assert.assertNotEquals( request, null );
    }
    
    /**
     * Test validate Lead Details request throws exception.
     */
    @Test( expectedExceptions = InvalidArgumentException.class )
    public void testValidateLeadDetailsRequestThrowsExceptionForField() {
        final LeadDetailsRequest request = new LeadDetailsRequest();
        request.setDirection( "desc" );
        request.setPage( 1 );
        request.setProperty( "abc" );
        request.setSize( 10 );
        requestValidator.validateLeadDetailsRequest( request );
        Assert.assertNotEquals( request, null );
    }
}
