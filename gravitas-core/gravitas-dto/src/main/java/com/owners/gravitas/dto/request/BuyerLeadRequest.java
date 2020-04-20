package com.owners.gravitas.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.owners.gravitas.enums.RecordType;

/**
 * The Class BuyerLeadRequest.
 *
 * @author vishwanathm
 *
 */
public class BuyerLeadRequest extends LeadRequest {

    /**
     * Instantiates a new buyer lead request.
     */
    public BuyerLeadRequest() {
        this.leadType = RecordType.BUYER.toString();
    }

    /**
     * Gets the record type.
     *
     * @return the record type
     */
    public String getLeadType() {
        return RecordType.BUYER.toString();
    }
    
    /**
     * Gets the source.
     *
     * @return the source
     */
    @NotBlank( message = "error.lead.source.required" )
    @Size( min = 1, max = 40, message = "error.lead.source.size" )
    public String getSource() {
        return super.getSource();
    }
}
