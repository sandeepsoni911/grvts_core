package com.owners.gravitas.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.owners.gravitas.validators.ValidateAgentLeadType;

/**
 * The Class AgentLeadRequest - to be used to create lead through agent app.
 * 
 * @author ankusht
 */
public class AgentLeadRequest extends LeadRequest {
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getPropertyAddress()
     */
    @Size( min = 0, max = 255, message = "error.lead.propertyAddress.size" )
    public String getPropertyAddress() {
        return super.getPropertyAddress();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getPreApprovedForMortgage()
     */
    @Size( min = 0, max = 50, message = "error.lead.preApprovedForMortgage.size" )
    public String getPreApprovedForMortgage() {
        return super.getPreApprovedForMortgage();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getBuyerReadinessTimeline()
     */
    @Size( min = 0, max = 50, message = "error.lead.buyerReadinessTimeline.size" )
    public String getBuyerReadinessTimeline() {
        return super.getBuyerReadinessTimeline();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getWorkingWithRealtor()
     */
    @Size( min = 0, max = 50, message = "error.lead.workingWithRealtor.size" )
    public String getWorkingWithRealtor() {
        return super.getWorkingWithRealtor();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getPriceRange()
     */
    @Size( min = 0, max = 200, message = "error.lead.priceRange.size" )
    public String getPriceRange() {
        return super.getPriceRange();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getInterestedZipcodes()
     */
    @Size( min = 0, max = 200, message = "error.lead.interestedZipcodes.size" )
    public String getInterestedZipcodes() {
        return super.getInterestedZipcodes();
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getLeadType()
     */
    @NotBlank( message = "error.lead.leadType.required" )
    @ValidateAgentLeadType
    public String getLeadType() {
        return super.getLeadType();
    }
}
