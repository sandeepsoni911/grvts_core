package com.owners.gravitas.exception.handler;

import static com.owners.gravitas.enums.ErrorCode.ACCESS_DENIED_ERROR;
import static com.owners.gravitas.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.owners.gravitas.enums.ErrorCode.JSON_CONVERSION_ERROR;
import static com.owners.gravitas.enums.ErrorCode.REQUEST_PARAM_MISSING_ERROR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonError.ErrorInfo;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException.Builder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.ErrorDetail;
import com.owners.gravitas.dto.OclEmailDto;
import com.owners.gravitas.dto.response.ErrorResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.InvalidArgumentException;
import com.owners.gravitas.util.PropertiesUtil;

// TODO: Auto-generated Javadoc
/**
 * Test class for {@link GlobalExceptionHandler}.
 *
 * @author vishwanathm
 */
public class GlobalExceptionHandlerTest extends AbstractBaseMockitoTest {

    /** The global exception handler. */
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    /** The message source. */
    @Mock
    private MessageSource messageSource;

    @BeforeTest
    public void beforeTest() throws Exception {
        final Map< String, String > props = new HashMap< String, String >();
        props.put( "ocl.loan.phase.prospect.stage", "New" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( props );
        } else {
            PropertiesUtil.getPropertiesMap().putAll( props );
        }
    }

    /** Test handleGeneralException method. */
    @Test
    public void testHandleGeneralException() {
        final Exception exception = new Exception( "This is test exception" );
        final ErrorResponse errorResponse = globalExceptionHandler.handleGeneralException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), INTERNAL_SERVER_ERROR.getCode() );
    }

    /** Test handleSecurityException method. */
    @Test
    public void testHandleSecurityException() {
        final ApplicationException exception = new ApplicationException( "This is test exception",
                JSON_CONVERSION_ERROR );
        final ErrorResponse errorResponse = globalExceptionHandler.handleSecurityException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), JSON_CONVERSION_ERROR.getCode() );
    }

    /**
     * Test handle http server error exception.
     */
    @Test
    public void testHandleHttpServerErrorException() {
        final HttpServerErrorException exception = new HttpServerErrorException( HttpStatus.INTERNAL_SERVER_ERROR );
        final ErrorResponse errorResponse = globalExceptionHandler.handleHttpServerErrorException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), INTERNAL_SERVER_ERROR.getCode() );
    }

    /**
     * Test handle http client error exception.
     */
    @Test
    public void testHandleHttpClientErrorException() {
        final HttpClientErrorException exception = new HttpClientErrorException( HttpStatus.INTERNAL_SERVER_ERROR );
        final ErrorResponse errorResponse = globalExceptionHandler.handleHttpClientErrorException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), INTERNAL_SERVER_ERROR.getCode() );
    }

    /**
     * Test handle validation exception with method argument not valid
     * exception.
     *
     * @throws NoSuchMethodException
     *             the no such method exception
     * @throws SecurityException
     *             the security exception
     */
    @Test
    public void testHandleValidationException_WithMethodArgumentNotValidException()
            throws NoSuchMethodException, SecurityException {
        final ErrorResponse errorResponse = globalExceptionHandler
                .handleValidationException( new MethodArgumentNotValidException(
                        new MethodParameter( globalExceptionHandler.getClass().getMethods()[0], 0 ),
                        new BindException( new String(), "test" ) ), new MockHttpServletRequest() );
        Assert.assertNotNull( errorResponse );
    }

    /**
     * Test handle validation exception with constraint violation exception.
     */
    @Test
    public void testHandleValidationException_WithConstraintViolationException() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        OclEmailDto dto = new OclEmailDto();
        dto.setFirstName( "465" );
        dto.setLastName( "dsf" );
        dto.setEmail( "a@a.com" );
        dto.setLoanNumber( "999999" );
        dto.setLoanPhase( "prospect" );
        final Set< ConstraintViolation< OclEmailDto > > oclDtoConstraintViolations = validator.validate( dto );
        final Map< String, List< String > > failedContraints = new LinkedMultiValueMap< String, String >();
        for ( final ConstraintViolation< OclEmailDto > constraintViolation : oclDtoConstraintViolations ) {
            List< String > violationList = failedContraints.get( constraintViolation.getPropertyPath().toString() );
            if (null == violationList) {
                violationList = new ArrayList<>();
            }
            violationList.add( constraintViolation.getMessageTemplate() );
            failedContraints.put( constraintViolation.getPropertyPath().toString(), violationList );
        }
        final ErrorResponse errorResponse = globalExceptionHandler.handleValidationException(
                new ConstraintViolationException( oclDtoConstraintViolations ), new MockHttpServletRequest() );
        Assert.assertNotNull( errorResponse );
        // final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        // Assert.assertEquals( errorDetail.getCode(),
        // INTERNAL_SERVER_ERROR.getCode() );
    }

    /**
     * Test handle binding exception.
     */
    @Test
    public void testHandleBindingException() {
        Mockito.when( messageSource.getMessage( Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                Mockito.any( Locale.class ) ) ).thenReturn( "test" );
        final BindException exception = new BindException( new String(), "test" );
        exception.addError( new FieldError( "LeadRequest", "firstName", "test" ) );
        ErrorResponse errorResponse = globalExceptionHandler.handleBindingException( exception,
                new MockHttpServletRequest() );
        Assert.assertNotNull( errorResponse );
    }

    /**
     * Test handle request parameter exception.
     */
    @Test
    public void testHandleRequestParameterException() {
        final MissingServletRequestParameterException exception = new MissingServletRequestParameterException( "name",
                "type" );
        final ErrorResponse errorResponse = globalExceptionHandler.handleRequestParameterException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), REQUEST_PARAM_MISSING_ERROR.getCode() );
    }

    /**
     * Test handle access denied exception.
     */
    @Test
    public void testHandleAccessDeniedException() {
        final AccessDeniedException exception = new AccessDeniedException( "This is test exception" );
        final ErrorResponse errorResponse = globalExceptionHandler.handleAccessDeniedException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), ACCESS_DENIED_ERROR.getCode() );
    }

    /**
     * Test handle http message not readable exception.
     */
    @Test
    public void testHandleHttpMessageNotReadableException() {
        final HttpMessageNotReadableException exception = new HttpMessageNotReadableException( "This is test exception",
                new Throwable() );
        final ErrorResponse errorResponse = globalExceptionHandler.handleHttpMessageNotReadableException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), INTERNAL_SERVER_ERROR.getCode() );
    }

    /**
     * Test process invalid argument errors.
     */
    @Test
    public void testHandleInvalidArgumentException() {
        List< String > list = new ArrayList<>();
        list.add( INTERNAL_SERVER_ERROR.getCode() );
        final InvalidArgumentException exception = new InvalidArgumentException( "This is test exception", list );
        final ErrorResponse errorResponse = globalExceptionHandler.handleInvalidArgumentException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), INTERNAL_SERVER_ERROR.getCode() );
    }

    /**
     */
    @Test
    public void testHandleGoogleJsonResponseException() {
        List< String > list = new ArrayList<>();
        list.add( INTERNAL_SERVER_ERROR.getCode() );
        HttpHeaders headers = new HttpHeaders();
        Builder builder = new Builder( 400, "page not found", headers );
        GoogleJsonError error = new GoogleJsonError();
        List< ErrorInfo > errors = new ArrayList<>();
        ErrorInfo info = new ErrorInfo();
        errors.add( info );
        info.setReason(  INTERNAL_SERVER_ERROR.getCode() );
        info.setMessage( "message" );
        error.setErrors( errors );
        final GoogleJsonResponseException exception = new GoogleJsonResponseException( builder, error );
        final ErrorResponse errorResponse = globalExceptionHandler.handleGoogleJsonResponseException( exception,
                new MockHttpServletRequest() );
        final ErrorDetail errorDetail = errorResponse.getErrors().get( 0 );
        Assert.assertEquals( errorDetail.getCode(), INTERNAL_SERVER_ERROR.getCode() );
    }
}
