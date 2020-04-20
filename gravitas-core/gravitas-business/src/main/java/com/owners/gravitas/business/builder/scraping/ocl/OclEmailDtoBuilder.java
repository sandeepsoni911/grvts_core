package com.owners.gravitas.business.builder.scraping.ocl;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;

import javax.mail.Message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.scraping.AbstractEmailLeadBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.OclEmailDto;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.AffiliateEmailParsingException;

/**
 * The Class OclEmailDtoBuilder.
 *
 * @author ankusht
 *
 *         The Class OclEmailDtoBuilder.
 */
@Component
public class OclEmailDtoBuilder extends AbstractEmailLeadBuilder {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OclEmailDtoBuilder.class );

    /** The email key. */
    private static final String EMAIL_KEY = "Email:";

    /** The first name key. */
    private static final String FIRST_NAME_KEY = "First Name:";

    /** The last name key. */
    private static final String LAST_NAME_KEY = "Last Name:";

    /** The loan phase key. */
    private static final String LOAN_PHASE_KEY = "Loan Phase:";

    /** The trigger event key. */
    private static final String TRIGGER_EVENT_KEY = "Trigger Event:";

    /** The assigned MLO key. */
    private static final String ASSIGNED_MLO_KEY = "Assigned MLO:";

    /** The status date key. */
    private static final String STATUS_DATE_KEY = "Status Date:";

    /** The note key. */
    private static final String NOTE_KEY = "Note:";

    /** The loan number key. */
    private static final String LOAN_NUMBER_KEY = "Loan Number:";

    /** The loan number length. */
    @Value( "${loan.number.length}" )
    private int loanNumberLength;

    /** The builder source prop. */
    @Value( "${ocl.builder.source}" )
    private String oclBuilderSourceProp;

    /** The builder source url prop. */
    @Value( "${ocl.builder.source.url}" )
    private String oclBuilderSourceUrlProp;

    /**
     * Convert to instance of {@LeadRequest}.
     *
     * @param source
     *            the message text
     * @return the map
     */
    @Override
    public GenericLeadRequest convertTo( final String source ) {
        return null;
    }

    /**
     * Convert to OCL email dto.
     *
     * @param message
     *            the message
     * @return the OCL email dto
     */
    public OclEmailDto convertToOclEmailDto( final Message message ) {
        try {
            final String messageText = getMessageStringPlain( getEmailBody( message ) ).trim();
            final String email = getLeadParameterValue( messageText, EMAIL_KEY, FIRST_NAME_KEY );
            final String firstName = getLeadParameterValue( messageText, FIRST_NAME_KEY, LAST_NAME_KEY );
            final String lastName = getLeadParameterValue( messageText, LAST_NAME_KEY, LOAN_PHASE_KEY );
            final String loanPhase = getLeadParameterValue( messageText, LOAN_PHASE_KEY, TRIGGER_EVENT_KEY );
            final String triggerEvent = getLeadParameterValue( messageText, TRIGGER_EVENT_KEY, ASSIGNED_MLO_KEY );
            final String assignedMLO = getLeadParameterValue( messageText, ASSIGNED_MLO_KEY, STATUS_DATE_KEY );
            final String statusDate = getLeadParameterValue( messageText, STATUS_DATE_KEY, NOTE_KEY );
            final String note = getLeadParameterValue( messageText, NOTE_KEY, LOAN_NUMBER_KEY );
            final int beginIndex = messageText.indexOf( LOAN_NUMBER_KEY ) + LOAN_NUMBER_KEY.length();
            final String loanNumber = messageText.substring( beginIndex, messageText.length() ).trim();

            final OclEmailDto oclEmailDto = new OclEmailDto();
            oclEmailDto.setAssignedMLO( assignedMLO );
            oclEmailDto.setEmail( email );
            oclEmailDto.setFirstName( firstName );
            oclEmailDto.setLastName( lastName );
            oclEmailDto.setLoanNumber( loanNumber );
            oclEmailDto.setLoanPhase( loanPhase );
            oclEmailDto.setNote( note );
            oclEmailDto.setStatusDate( statusDate );
            oclEmailDto.setTriggerEvent( triggerEvent );
            return oclEmailDto;
        } catch ( final AffiliateEmailParsingException e ) {
            LOGGER.error( "IGNORE: " + e.getMessage(), e );
            throw new AffiliateEmailParsingException( message, e.getMessage(), e.getCause() );
        } catch ( final Exception e ) {
            LOGGER.error( "IGNORE: " + e.getMessage(), e );
            throw new AffiliateEmailParsingException( message, "Error occurred while parsing OCL email message", e );
        }
    }

    /**
     * Convert to.
     *
     * @param dto
     *            the dto
     * @param company
     *            the company
     * @param source
     *            the source
     * @param sourceUrl
     *            the source url
     * @return the generic lead request
     */
    public GenericLeadRequest convertTo( final OclEmailDto dto ) {
        final String company = getName( dto.getFirstName(), dto.getLastName() ) + Constants.BLANK_SPACE
                + Constants.OPENING_BRACKET + dto.getEmail() + Constants.CLOSING_BRACKET;
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        genericLeadRequest.setEmail( dto.getEmail() );
        genericLeadRequest.setFirstName( dto.getFirstName() );
        genericLeadRequest.setLastName( dto.getLastName() );
        genericLeadRequest.setCompany( company );
        genericLeadRequest.setSource( oclBuilderSourceProp );
        genericLeadRequest.setLeadType( RecordType.OWNERS_COM_LOANS.name() );
        genericLeadRequest.setLeadSourceUrl( oclBuilderSourceUrlProp );
        genericLeadRequest.setRequestType( LeadRequestType.OTHER.name() );
        genericLeadRequest.setLoanNumber( Integer.parseInt( dto.getLoanNumber() ) );
        return genericLeadRequest;
    }

    /**
     * Gets the name.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @return the name
     */
    private String getName( final String firstName, final String lastName ) {
        String name = lastName;
        if (StringUtils.isNotBlank( firstName )) {
            name = firstName + BLANK_SPACE + lastName;
        }
        return name;
    }
}
