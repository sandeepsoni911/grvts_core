package com.owners.gravitas.business;

import java.util.Set;

import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.enums.BuyerFarmType;

/**
 * The Interface BuyerRegistrationBusinessService.
 *
 * @author vishwanathm
 */
public interface BuyerFarmingBusinessService {

    /**
     * Register buyer.
     *
     * @param leadSource
     *            the lead source
     * @return the contact
     */
    Contact registerBuyer( LeadSource leadSource );

    /**
     * Update farming status.
     *
     * @param crmId
     *            the crm id
     * @param buyerFarmType
     *            the buyer farm type
     * @param isOpportunity
     *            the is opportunity
     */
    void updateFarmingStatus( String crmId, BuyerFarmType buyerFarmType, boolean isOpportunity );

    /**
     * Checks if is buyer auto registration email.
     *
     * @param emailStr
     *            the email str
     * @return true, if is buyer auto registration email
     */
    boolean isBuyerAutoRegistrationEmail( String emailStr );

    /**
     * Gets the generic lead request.
     *
     * @param leadSource
     *            the lead source
     * @return the generic lead request
     */
    GenericLeadRequest getGenericLeadRequest( LeadSource leadSource );

    /**
     * Save search.
     *
     * @param leadSource
     *            the lead source
     */
    void saveSearch( LeadSource leadSource );

    /**
     * Send web activity followup email.
     *
     * @param buyerWebActivitySource
     *            the buyer web activity source
     */
    void sendWebActivityFollowupEmail( BuyerWebActivitySource buyerWebActivitySource );

    /**
     * Process web activity.
     *
     * @param buyerWebActivitySource
     *            the buyer web activity source
     */
    void processWebActivity( BuyerWebActivitySource buyerWebActivitySource );

    /**
     * Send followup email.
     *
     * @param executionId
     *            the execution id
     * @param leadSource
     *            the lead source
     */
    void sendFollowupEmail( String executionId, LeadSource leadSource );

    /**
     * Checks if is lead state is among long term farm state.
     *
     * @param state
     *            the state str
     * 
     * @return true, if is lead state is in long term state string
     */
    public boolean isFarmLongTermState( final String state );

    /**
     * Sets the up save search followup process.
     *
     * @param leadSource
     *            the new up save search followup process
     */
    void initiateSaveSearchFollowupProcess( final LeadSource leadSource );
    
}
