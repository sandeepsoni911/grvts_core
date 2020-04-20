package com.owners.gravitas.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.LeadSourceName;
import com.owners.gravitas.enums.RecordType;

/**
 * The Class OclLeadRequest.
 *
 * @author kushwaja
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class OclLeadRequest {

    @NotBlank( message = "error.lead.ocl.crm.id" )
    @Size( min = 1, max = 30, message = "error.lead.ocl.crm.id.size" )
    protected String crmId;

    /** The record type. */
    protected String leadType;

    /** The source. */
    protected String source;

    /** first name. */
    @Size( min = 0, max = 40, message = "error.lead.firstName.size" )
    protected String firstName;

    /** last name. */
    //@NotBlank( message = "error.lead.lastName.required" )
    @Size( min = 1, max = 80, message = "error.lead.lastName.size" )
    protected String lastName;

    /** The lead source url. */
    private String leadSourceUrl;

    /** The request type. */
    private String requestType;
    
    public OclLeadRequest() {
        
    }

    /**
     * Instantiates a new ocl lead request.
     */
    public OclLeadRequest( String crmId ) {
        this.crmId = crmId;
        this.leadType = RecordType.OWNERS_COM_LOANS.toString();
        this.requestType = LeadRequestType.OTHER.toString();
        this.source = LeadSourceName.OWNERS_AGENT_APP.getSource();
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId( String crmId ) {
        this.crmId = crmId;
    }

    public String getLeadType() {
        return leadType;
    }

    public void setLeadType( String leadType ) {
        this.leadType = leadType;
    }

    public String getSource() {
        return source;
    }

    public void setSource( String source ) {
        this.source = source;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getLeadSourceUrl() {
        return leadSourceUrl;
    }

    public void setLeadSourceUrl( String leadSourceUrl ) {
        this.leadSourceUrl = leadSourceUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType( String requestType ) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "OclLeadRequest [crmId=" + crmId + ", leadType=" + leadType + ", source=" + source + ", firstName="
                + firstName + ", lastName=" + lastName + ", leadSourceUrl=" + leadSourceUrl + ", requestType="
                + requestType + "]";
    }
}
