/**
 *
 */
package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.CRM_APPEND_SEPERATOR;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.owners.gravitas.dto.CRMBaseDTO;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.CRMObject;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class DeDupeAbstractBuilder.
 *
 * @author harshads
 * @param <A>
 *            the generic type
 * @param <B>
 *            the generic type
 */
public abstract class DeDupeAbstractBuilder< A, B > extends AbstractBuilder< A, B > {

    /** The Constant COLON. */
    private static final String COLON = ":";

    /** The Constant NONE_VALUE. */
    private static final String NONE_VALUE = "None";

    /** The Constant NEW_LINE. */
    protected static final String NEW_LINE = "\n";

    /** The Constant AUDIT_RECORD_MESSAGE. */
    protected static final String AUDIT_RECORD_MESSAGE = "Lead Data merged to existing %s on %s values are:" + NEW_LINE
            + "%s";

    /**
     * Sets the company.
     *
     * @param source
     *            the source
     * @param crmBaseDTO
     *            the crm lead request
     * @param auditBuilder
     *            the audit builder
     * @param isConsidered
     *            the is considered
     */
    protected void setCompany( final LeadRequest source, final CRMBaseDTO crmBaseDTO, final StringBuilder auditBuilder,
            final boolean isConsidered ) {
        String company = source.getCompany();
        if (isConsidered) {
            if (StringUtils.isBlank( company )) {
                company = ( StringUtils.isNotBlank( source.getFirstName() ) ? source.getFirstName() + BLANK_SPACE
                        : EMPTY ) + source.getLastName() + " (" + source.getEmail() + ")";
            }
            if (null == auditBuilder) {
                crmBaseDTO.setCompany( company );
            } else {
                crmBaseDTO
                        .setCompany( replaceNotMatching( crmBaseDTO.getCompany(), company, auditBuilder, "Company" ) );
            }
        }
    }

    /**
     * Append value.
     *
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @param auditBuilder
     *            the audit builder
     * @param propertyName
     *            the property name
     * @return the string
     */
    protected String appendValue( final String oldValue, final String newValue, final StringBuilder auditBuilder,
            final String propertyName ) {
        if (StringUtils.isNotBlank( newValue )) {
            auditBuilder.append( propertyName + COLON + newValue + NEW_LINE );
        }
        return makeEmptyIfBlank( oldValue ) + CRM_APPEND_SEPERATOR + makeEmptyIfBlank( newValue );
    }

    /**
     * Make empty if blank- Convert string to EMPTY is value is blank(null,
     * "<Blank space>" and "<Any number of blank spaces>").
     *
     * @param newValue
     *            the new value
     * @return the string
     */
    private String makeEmptyIfBlank( final String newValue ) {
        return StringUtils.isBlank( newValue ) ? EMPTY : newValue;
    }

    /**
     * Make null if empty.
     *
     * @param paramValue
     *            the param value
     * @return the char sequence
     */
    private CharSequence makeNullIfEmpty( final String paramValue ) {
        return StringUtils.isBlank( paramValue ) ? null : paramValue;
    }

    /**
     * Replace not matching.
     *
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @param auditBuilder
     *            the audit builder
     * @param propertyName
     *            the property name
     * @return the string
     */
    protected String replaceNotMatching( final String oldValue, final String newValue, final StringBuilder auditBuilder,
            final String propertyName ) {
        String returnValue = oldValue;
        if (newValue != null && !isEqual( oldValue, newValue )) {
            returnValue = newValue;
            if (null != auditBuilder) {
                auditBuilder.append( propertyName + COLON + returnValue + NEW_LINE );
            }
        }
        return returnValue;
    }

    /**
     * Replace not matching without audit trace.
     *
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @return the string
     */
    protected String replaceNotMatching( final String oldValue, final String newValue ) {
        return StringUtils.isNotBlank( newValue ) ? newValue : oldValue;
    }

    /**
     * Checks if is equal.
     *
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @return true, if successful
     */
    private boolean isEqual( final String oldValue, final String newValue ) {
        return StringUtils.equalsIgnoreCase( makeNullIfEmpty( oldValue ), newValue );
    }

    /**
     * Gets the new audit record value.
     *
     * @param auditBuilder
     *            the audit builder
     * @return the new audit record value
     */
    protected String getNewAuditRecordValue( final StringBuilder auditBuilder, final CRMObject crmObject ) {
        final String auditRecordValue = auditBuilder.toString();
        return String.format( AUDIT_RECORD_MESSAGE, crmObject.getName(),
                DateUtil.toString( new DateTime(), DEFAULT_CRM_DATE_PATTERN ),
                StringUtils.isEmpty( auditRecordValue ) ? NONE_VALUE : auditRecordValue );
    }

    /**
     * Replace not matching numbers.
     *
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @param auditBuilder
     *            the audit builder
     * @param propertyName
     *            the property name
     * @return the string
     */
    protected String replaceNotMatchingNumbers( final String oldValue, final String newValue,
            final StringBuilder auditBuilder, final String propertyName ) {
        String returnValue = oldValue;
        if (isNotEqual( oldValue, newValue )) {
            returnValue = newValue;
            auditBuilder.append( propertyName + COLON + returnValue + NEW_LINE );
        }
        return returnValue;
    }

    /**
     * Checks if is not equal.
     *
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @return true, if successful
     */
    private boolean isNotEqual( final String oldValue, final String newValue ) {
        boolean notEqual = false;
        if (StringUtils.isEmpty( oldValue ) && StringUtils.isNotEmpty( newValue )) {
            notEqual = true;
        } else if (( StringUtils.isNotEmpty( oldValue ) && StringUtils.isNotEmpty( newValue ) )
                && ( Double.valueOf( oldValue ).compareTo( Double.valueOf( newValue ) ) != 0 )) {
            notEqual = true;
        }
        return notEqual;
    }

    /**
     * Gets the request type.
     *
     * @param sourceRequestType
     *            the source request type
     * @param crmRequestType
     *            the crm request type
     * @param auditBuilder
     *            the audit builder
     * @return the request type
     */
    protected String getRequestType( final String sourceRequestType, final String crmRequestType,
            final StringBuilder auditBuilder ) {
        String requestType = null;
        if (StringUtils.isNotBlank( sourceRequestType )) {
            requestType = LeadRequestType.valueOf( LeadRequestType.class, sourceRequestType ).getType();
            if (null != auditBuilder && null != crmRequestType && !requestType.equalsIgnoreCase( crmRequestType )) {
                auditBuilder.append( "Lead Request Type" + COLON + sourceRequestType + NEW_LINE );
            }
        }
        return requestType;
    }

    /**
     * Sets the date fields.
     *
     * @param source
     *            the source
     * @param crmLeadRequest
     *            the crm lead request
     */
    protected void setDateFields( final LeadRequest source, final CRMBaseDTO crmLeadRequest,
            final StringBuilder auditBuilder ) {
        crmLeadRequest.setListingCreationDate(
                getDateTime( source.getListingCreationDate(), crmLeadRequest.getListingCreationDate(),
                        DateUtil.DEFAULT_CRM_DATE_PATTERN, auditBuilder, "Listing Creation Date" ) );
        crmLeadRequest.setLastVisitDateTime(
                getDateTime( source.getLastVisitDateTime(), crmLeadRequest.getLastVisitDateTime(),
                        DateUtil.DATE_TIME_PATTERN, auditBuilder, "Last Visit Date Time" ) );
        crmLeadRequest.setInquiryDate(
                getDateTime( source.getInquiryDate(), crmLeadRequest.getInquiryDate(),
                        DateUtil.DEFAULT_CRM_DATE_PATTERN, auditBuilder, "Inquiry Date Time" ) );
        
    }

    /**
     * Gets the date time.
     *
     * @param sourceDateTime
     *            the source date time
     * @param crmDateTime
     *            the crm date time
     * @param auditBuilder
     *            the audit builder
     * @param propertyName
     *            the property name
     * @return the date time
     */
    private DateTime getDateTime( final String sourceDateTime, final DateTime crmDateTime, final String datePattern,
            final StringBuilder auditBuilder, final String propertyName ) {
        final String newDateTime = replaceNotMatching( DateUtil.toString( crmDateTime, datePattern ), sourceDateTime,
                auditBuilder, propertyName );
        return DateUtil.toDateTime( newDateTime, datePattern );
    }

    /**
     * Handle special cases.
     *
     * @param source
     *            the source
     * @param crmLeadRequest
     *            the crm lead request
     * @param auditBuilder
     *            the audit builder
     */
    protected void handleSpecialCases( final LeadRequest source, final CRMBaseDTO crmLeadRequest,
            final StringBuilder auditBuilder, final boolean isConsidered ) {
        setCompany( source, crmLeadRequest, auditBuilder, isConsidered );
        crmLeadRequest.setRequestType(
                getRequestType( source.getRequestType(), crmLeadRequest.getRequestType(), auditBuilder ) );
        setDateFields( source, crmLeadRequest, auditBuilder );
    }

    /**
     * Gets the audit record.
     *
     * @param oldAuditRecord
     *            the old audit record
     * @param crmObject
     *            the crm object
     * @param auditBuilder
     *            the audit builder
     * @return the audit record
     */
    protected String getAuditRecord( final String oldAuditRecord, final CRMObject crmObject,
            final StringBuilder auditBuilder ) {
        String newAuditRecord = StringUtils.isNotEmpty( oldAuditRecord ) ? oldAuditRecord : StringUtils.EMPTY;
        newAuditRecord += NEW_LINE + NEW_LINE + getNewAuditRecordValue( auditBuilder, crmObject );
        return newAuditRecord;
    }
}
