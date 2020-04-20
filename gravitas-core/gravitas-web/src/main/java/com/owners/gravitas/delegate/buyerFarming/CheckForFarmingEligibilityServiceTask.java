package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.enums.GravitasProcess.LEAD_MANAGEMENT_PROCESS;
import static com.owners.gravitas.enums.LeadStatus.NEW;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY;

import org.flowable.engine.delegate.DelegateExecution;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.delegate.BaseServiceTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.service.ContactEntityService;

/**
 * The Class CheckForBuyerRegistrationServiceTask.
 *
 * @author vishwanathm
 */
@Service( "checkForFarmingEligibilityServiceTask" )
public class CheckForFarmingEligibilityServiceTask extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CheckForFarmingEligibilityServiceTask.class );

    /** The contact service v1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The process management business service. */
    @Autowired
    protected ProcessBusinessService processBusinessService;
    /** The buyer registration business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The buyer registration config. */
    @Autowired
    private BuyerFarmingConfig buyerFarmingConfig;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.delegate.BaseServiceTask#execute(org.flowable.engine.
     * delegate.DelegateExecution)
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        LOGGER.debug( "Let's see is buyer is new or existing and also whether he/she is registered in gravitas." );
        final String executionId = execution.getId();
        final LeadSource leadSource = getLeadSource( executionId );
        String email = null;
        try {
            email = leadSource.getEmail();
            boolean isFarmingRequired = FALSE;
            boolean isOpportunity = FALSE;

            final Contact contact = contactServiceV1.getContact( email, BUYER.getType() );
            if (contact != null && contact.getObjectType().getName().equals( OPPORTUNITY.toLowerCase() )) {
                LOGGER.info( "Request is for : {}, for email : {} and farmingType :{}",
                        contact.getObjectType().getName(), email, leadSource.getFarmingBuyerAction() );
                processBusinessService.deActivateAllProcess( email, GravitasProcess.INSIDE_SALES_FARMING_PROCESS,
                        null );
                runtimeService.setVariable( executionId, "registered", TRUE );
                runtimeService.setVariable( executionId, "existingContact", FALSE );
                isOpportunity = TRUE;
                isFarmingRequired = TRUE;
            } else if (buyerFarmingConfig.isBuyerAutoRegistrationEnabled()
                    && buyerFarmingBusinessService.isBuyerAutoRegistrationEmail( email )
                    && buyerFarmingBusinessService.isFarmLongTermState( leadSource.getState() )
                    && !email.startsWith(Constants.COMMON_LEAD_PREFIX+Constants.COMMON_EMAIL_PREFIX)) {
                boolean registered = FALSE;
                boolean existingContact = FALSE;
                isFarmingRequired = TRUE;
                if (contact != null) {
                    existingContact = TRUE;
                    if (StringUtils.isNotBlank( contact.getOwnersComId() )) {
                        LOGGER.debug( "Buyer " + email + " has already registered in gravitas." );
                        registered = TRUE;
                    }
                    deActivateExistingInsideSalesFarming( executionId, email );
                }
                processBusinessService.createProcess( email, leadSource.getId(), executionId, LEAD_MANAGEMENT_PROCESS );
                runtimeService.setVariable( executionId, "registered", registered );
                runtimeService.setVariable( executionId, "existingContact", existingContact );
                LOGGER.info(
                        "Buyer: " + email + "  existing buyer? " + existingContact + " registered?  " + registered );
            }
            runtimeService.setVariable( executionId, "isOpportunity", isOpportunity );
            runtimeService.setVariable( executionId, "isFarmingRequired", isFarmingRequired );
            LOGGER.info( "runTime Check parameters for email :{} isFarmingRequired : {}, isOpportunity : {}", email,
                    isFarmingRequired, isOpportunity );
        } catch ( final Exception e ) {
            LOGGER.error( "Exception occurred while checking farming eligibility : {}", email, e );
            setVariablesInParentProcess( execution, leadSource, e, LEAD_MANAGEMENT_PROCESS );
        }

    }

    /**
     * De activate existing inside sales farming.
     *
     * @param executionId
     *            the execution id
     * @param email
     *            the email
     */
    private void deActivateExistingInsideSalesFarming( final String executionId, final String email ) {
        runtimeService.setVariable( executionId, "leadStatus", NEW.name() );
        runtimeService.setVariable( executionId, "processWebActivity", false );
        processBusinessService.deActivateAndSignal( email, GravitasProcess.INSIDE_SALES_FARMING_PROCESS, null );
    }
}
