package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.ErrorDetail;

/**
 * This is test class for ErrorResponse.
 *
 * @author vishwanathm
 */
public class ErrorResponseTest {

    /** The error response. */
    private ErrorResponse errorResponse = new ErrorResponse();

    /** The error list. */
    private List< ErrorDetail > errorList;

    /**
     * This method initialises errorResponse object to test.
     */
    @BeforeClass
    public final void initialiseErrorList() {
        errorList = new ArrayList< ErrorDetail >();
        errorList.add( new ErrorDetail( "error1", "detail1" ) );
        errorList.add( new ErrorDetail( "error2", "detail1" ) );
    }

    /**
     * This is test method for setError and getError.
     */
    @Test
    public final void testSetError() {
        errorResponse.setErrors( errorList );
        final List< ErrorDetail > temp = errorResponse.getErrors();
        Assert.assertSame( temp.get( 0 ).getCode(), "error1", "Error not returned properly" );
    }

    /**
     * This is test method for setError and getError.
     */
    @Test
    public final void testAddError() {
        errorResponse.addError( "error3", "error detail" );
        final List< ErrorDetail > temp = errorResponse.getErrors();
        Assert.assertSame( temp.get( 0 ).getCode(), "error3", "Error3 not added properly" );
    }

    /**
     * This is test method for setErrorId and getErrorId.
     */
    @Test
    public final void testAddErrorId() {
        errorResponse.setErrorId( "testErrorId" );
        Assert.assertSame( errorResponse.getErrorId(), "testErrorId" );
    }

}
