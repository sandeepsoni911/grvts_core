package com.owners.gravitas.exception.handler;

import static com.owners.gravitas.enums.ErrorCode.ACCESS_DENIED_ERROR;
import static com.owners.gravitas.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.owners.gravitas.enums.ErrorCode.INVALID_FORMAT_ERROR;
import static com.owners.gravitas.enums.ErrorCode.REQUEST_PARAM_MISSING_ERROR;
import static com.owners.gravitas.enums.ErrorCode.THIRD_PARTY_API_ERROR;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.api.client.googleapis.json.GoogleJsonError.ErrorInfo;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.dto.SlackError;
import com.owners.gravitas.dto.response.ErrorResponse;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.InvalidArgumentException;
import com.owners.gravitas.exception.OpportunityNotAssignedException;
import com.owners.gravitas.managers.ThreadLocalManager;
import com.owners.gravitas.util.ErrorTokenGenerator;
import com.owners.gravitas.util.GravitasWebUtil;

/**
 * General error handler for the application.
 *
 * @author vishwanathm
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GlobalExceptionHandler.class );

    /** The Constant AGENTS_API_URI. */
    private static final String AGENTS_API_URI = "/api/agents";

    /** The Constant WEB_API_URI. */
    private static final String WEB_API_URI = "/webapi";

    /** The message source. */
    @Autowired
    private MessageSource messageSource;

    /** The agent business service. */
    @Autowired
    private AgentBusinessService agentBusinessService;

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    /**
     * This method is handler for generic Exceptions.
     *
     * @param exception
     *            that needs to be handled
     * @param request
     *            the request
     * @return ErrorResponse that needs to be send back
     */
    @ExceptionHandler( value = Exception.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponse handleGeneralException( final Exception exception, final HttpServletRequest request ) {
        return handleException( exception, INTERNAL_SERVER_ERROR, request, null );
    }

    /**
     * This method is handler for generic Exceptions.
     *
     * @param exception
     *            that needs to be handled
     * @param request
     *            the request
     * @return ErrorResponse that needs to be send back
     */
    @ExceptionHandler( value = AccessDeniedException.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.FORBIDDEN )
    public ErrorResponse handleAccessDeniedException( final AccessDeniedException exception,
            final HttpServletRequest request ) {
        return handleException( exception, ACCESS_DENIED_ERROR, request, null );
    }

    /**
     * Handle http message not readable exception.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the error response
     */
    @ExceptionHandler( value = HttpMessageNotReadableException.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponse handleHttpMessageNotReadableException( final HttpMessageNotReadableException exception,
            final HttpServletRequest request ) {
        ErrorCode errorCode = INTERNAL_SERVER_ERROR;
        if (InvalidFormatException.class.isAssignableFrom( exception.getCause().getClass() )) {
            errorCode = INVALID_FORMAT_ERROR;
        }
        ErrorResponse response = handleException( exception, errorCode, request, null );
        response.getErrors().get( 0 ).setDetail( exception.getMostSpecificCause().getLocalizedMessage() );
        return response;
    }

    /**
     * This method is handler for Exceptions arising from an application.
     *
     * @param exception
     *            that has been thrown
     * @param request
     *            the request
     * @return ErrorResponse response to send back
     */
    @ExceptionHandler( value = ApplicationException.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponse handleSecurityException( final ApplicationException exception,
            final HttpServletRequest request ) {
        return handleException( exception, exception.getErrorCode(), request, null );
    }

    /**
     * Handle http server error exception.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the error response
     */
    @ExceptionHandler( value = HttpServerErrorException.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponse handleHttpServerErrorException( final HttpServerErrorException exception,
            final HttpServletRequest request ) {
        return handleException( exception, INTERNAL_SERVER_ERROR, request, exception.getResponseBodyAsString() );
    }

    /**
     * Handle http server error exception.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the error response
     */
    @ExceptionHandler( value = HttpClientErrorException.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponse handleHttpClientErrorException( final HttpClientErrorException exception,
            final HttpServletRequest request ) {
        return handleException( exception, INTERNAL_SERVER_ERROR, request, exception.getResponseBodyAsString() );
    }

    /**
     * Validation exception handler.
     *
     * @param exception
     *            validation exception
     * @param request
     *            the request
     * @return ErrorResponse response to send back
     */
    @ExceptionHandler( value = { MethodArgumentNotValidException.class } )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public ErrorResponse handleValidationException( final MethodArgumentNotValidException exception,
            final HttpServletRequest request ) {
        final String errorId = logException( exception, request, INTERNAL_SERVER_ERROR, null );
        final ErrorResponse error = processBindingResult( exception.getBindingResult() );
        error.setErrorId( errorId );
        return error;
    }

    /**
     * Validation exception handler.
     *
     * @param exception
     *            validation exception
     * @param request
     *            the request
     * @return ErrorResponse response to send back
     */
    @ExceptionHandler( value = { ConstraintViolationException.class } )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public ErrorResponse handleValidationException( final ConstraintViolationException exception,
            final HttpServletRequest request ) {
        final String errorId = logException( exception, request, INTERNAL_SERVER_ERROR, null );
        final ErrorResponse error = processBindingResult( exception.getConstraintViolations() );
        error.setErrorId( errorId );
        return error;
    }

    /**
     * Handle invalid argument exception.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the error response
     */
    @ExceptionHandler( value = { InvalidArgumentException.class } )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public ErrorResponse handleInvalidArgumentException( final InvalidArgumentException exception,
            final HttpServletRequest request ) {
        final String errorId = logException( exception, request, INTERNAL_SERVER_ERROR, null );
        final ErrorResponse error = processInvalidArgumentErrors( exception.getErrors() );
        error.setErrorId( errorId );
        return error;
    }

    /**
     * Parameter binding exception handler.
     *
     * @param exception
     *            binding exception
     * @param request
     *            the request
     * @return ErrorResponse response to send back
     */
    @ExceptionHandler( value = { BindException.class } )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public ErrorResponse handleBindingException( final BindException exception, HttpServletRequest request ) {
        final String errorId = logException( exception, request, INTERNAL_SERVER_ERROR, null );
        final ErrorResponse error = processBindingResult( exception.getBindingResult() );
        error.setErrorId( errorId );
        return error;
    }

    /**
     * Handle request parameter exception.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the error response
     */
    @ExceptionHandler( value = { MissingServletRequestParameterException.class } )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public ErrorResponse handleRequestParameterException( final MissingServletRequestParameterException exception,
            HttpServletRequest request ) {
        return handleException( exception, REQUEST_PARAM_MISSING_ERROR, request, null );
    }

    /**
     * Handle google json response exception.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the error response
     */
    @ExceptionHandler( value = GoogleJsonResponseException.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.NOT_FOUND )
    public ErrorResponse handleGoogleJsonResponseException( final GoogleJsonResponseException exception,
            final HttpServletRequest request ) {
        final String errorId = logException( exception, request, THIRD_PARTY_API_ERROR, null );
        final ErrorResponse errors = new ErrorResponse();
        for ( ErrorInfo error : exception.getDetails().getErrors() ) {
            errors.addError( error.getReason(), error.getMessage() );
        }
        errors.setErrorId( errorId );
        return errors;
    }
    
    /**
     * This method is handler for Exceptions arising from an application.
     *
     * @param exception
     *            that has been thrown
     * @param request
     *            the request
     * @return ErrorResponse response to send back
     */
    @ExceptionHandler( value = OpportunityNotAssignedException.class )
    @ResponseBody
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public ErrorResponse handleSecurityException( final OpportunityNotAssignedException exception,
            final HttpServletRequest request ) {
         ErrorResponse errorResponse = handleException( exception, exception.getErrorCode(), request, null );
         return errorResponse;
    }

    /**
     * Handle invalid argument exception.
     *
     * @param exception
     *            the exception
     * @param request
     *            the request
     * @return the error response
     */
    private ErrorResponse processBindingResult( Set< ConstraintViolation< ? > > constraintViolations ) {
        final ErrorResponse errorResponse = new ErrorResponse();
        constraintViolations.stream().forEach( fieldError -> errorResponse.addError( fieldError.getMessage(),
                messageSource.getMessage( fieldError.getMessage(), null, null, Locale.getDefault() ) ) );
        return errorResponse;
    }

    /**
     * Process binding result for error response.
     *
     * @param bindingResult
     *            to operate on
     * @return error response built from binding result
     */
    private ErrorResponse processBindingResult( final BindingResult bindingResult ) {
        final ErrorResponse errorResponse = new ErrorResponse();
        sortFieldErrors( bindingResult.getFieldErrors() ).stream()
                .forEach( fieldError -> errorResponse.addError( fieldError.getDefaultMessage(),
                        messageSource.getMessage( fieldError.getDefaultMessage(), null, fieldError.getDefaultMessage(),
                                Locale.getDefault() ) ) );
        return errorResponse;
    }

    /**
     * Process invalid argument errors.
     *
     * @param errorList
     *            the error list
     * @return the error response
     */
    private ErrorResponse processInvalidArgumentErrors( final List< String > errorList ) {
        final ErrorResponse errorResponse = new ErrorResponse();
        for ( String error : errorList ) {
            errorResponse.addError( error, messageSource.getMessage( error, null, "", Locale.getDefault() ) );
        }
        return errorResponse;
    }

    /**
     * Sort field errors on each field name.
     *
     * @param validationErrors
     *            the validation errors
     * @return the list
     */
    private List< FieldError > sortFieldErrors( final List< FieldError > validationErrors ) {
        final List< FieldError > errors = new ArrayList<>( validationErrors );
        errors.sort( new Comparator< FieldError >() {
            @Override
            public int compare( FieldError fieldError1, FieldError fieldError2 ) {
                return fieldError1.getField().compareTo( fieldError2.getField() );
            }
        } );
        return errors;
    }

    /**
     * This method is handler for generic Exceptions.
     *
     * @param exception
     *            that needs to be handled
     * @param errorCode
     *            the error code
     * @param request
     *            the request
     * @param otherErrorLog
     *            the other error log
     * @return ErrorResponse that needs to be send back
     */
    private ErrorResponse handleException( final Exception exception, final ErrorCode errorCode,
            HttpServletRequest request, final String otherErrorLog ) {
        final String errorId = logException( exception, request, errorCode, otherErrorLog );
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.addError( errorCode.getCode(), errorCode.getErrorDetail() );
        errorResponse.setErrorId( errorId );
        return errorResponse;
    }

    /**
     * This method is used to log Exceptions.
     *
     * @param exception
     *            that needs to be logged
     * @param request
     *            the request
     * @param otherErrorLog
     *            the other error log
     * @return Error id
     */
    private String logException( final Throwable exception, final HttpServletRequest request, final ErrorCode errorCode,
            final String otherErrorLog ) {
        StringBuilder slackErrorMessage = new StringBuilder();
        if (otherErrorLog != null) {
            LOGGER.error( otherErrorLog );
            slackErrorMessage.append( otherErrorLog + "\n" );
        }
        final String errorId = ErrorTokenGenerator.getErrorId();
        final StringBuilder error = new StringBuilder( "Error id->" + errorId );
        error.append( "\n" + exception.getLocalizedMessage() );
        LOGGER.error( error.toString(), exception );
        slackErrorMessage.append( exception.getLocalizedMessage() );

        postErrorToSlack( exception, errorCode, request, errorId, slackErrorMessage.toString() );

        return errorId;
    }

    /**
     * Post error details to slack channel.
     *
     * @param exception
     *            the exception
     * @param errorResponse
     *            the error
     * @param request
     *            the request
     */
    private void postErrorToSlack( final Throwable exception, final ErrorCode errorCode,
            final HttpServletRequest request, final String errorId, final String errorMessage ) {
        if (request.getRequestURI() != null && ( request.getRequestURI().startsWith( AGENTS_API_URI )
                || request.getRequestURI().startsWith( WEB_API_URI ) )) {
            final SlackError error = new SlackError();
            error.setErrorCode( errorCode );
            error.setException( exception );
            error.setErrorId( errorId );
            error.setErrorMessage( errorMessage );
            error.setRequestUrl( request.getRequestURL().toString() );
            error.setUser( gravitasWebUtil.getAppUserEmail() );
            error.setRequestParams( ThreadLocalManager.getInstance().getRequestParams() );
            agentBusinessService.postErrorToSlack( error );
        }
        ThreadLocalManager.getInstance().removeRequestParams();
    }

}
