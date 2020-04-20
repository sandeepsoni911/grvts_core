/**
 *
 */
package com.owners.gravitas.handler;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.constants.Constants.SEMI_COLON;
import static com.owners.gravitas.enums.BuyerFarmType.LONG_TERM_BUYER;
import static com.owners.gravitas.enums.BuyerFarmType.LOST_BUYER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.OpportunityChangeType;
import com.owners.gravitas.enums.RecordType;

/**
 * @author harshads
 *
 */
@OpportunityChange( type = OpportunityChangeType.Stage, value = "Closed Lost" )
public class ClosedLostStageChangeHandler extends OpportunityChangeHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ClosedLostStageChangeHandler.class );

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The buyer registration business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The process management business service. */
    @Autowired
    protected ProcessBusinessService processBusinessService;

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    /** The lost reasons str. */
    @Value( value = "${buyer.farming.opportunity.considered.lost.reasons}" )
    private String lostReasonsStr;

    /** The lost reasons. */
    private final List< String > lostReasons = new ArrayList<>();

    /**
     * Inits the data builder.
     */
    @PostConstruct
    private void initDataBuilder() {
        Stream.of( lostReasonsStr.split( COMMA ) ).forEach( value -> lostReasons.add( value.trim() ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#handleChange(java.
     * lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse handleChange( final String agentId, final String opportunityId, final Contact contact ) {
        final String crmOpportunityId = opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE )
                .getContact().getCrmId();
        final OpportunitySource opportunity = opportunityBusinessService.getOpportunity( crmOpportunityId );
        LOGGER.info(
                "opportunity" + "(" + crmOpportunityId + ") is lost, & the reason is:" + opportunity.getReasonLost() );
        BuyerFarmType farmType = LOST_BUYER;
        final String reasons = StringUtils.isNotBlank( opportunity.getReasonLost() )
                ? opportunity.getReasonLost().trim()
                : BLANK_SPACE;
        final List< String > oppLostReasons = new ArrayList<>();
        Collections.addAll( oppLostReasons, reasons.split( SEMI_COLON ) );
        for ( final String reason : oppLostReasons ) {
            LOGGER.info( "lostReasonsList :{} and reason" + lostReasons, reason );
            if (lostReasons.contains( reason )) {
                farmType = LONG_TERM_BUYER;
                break;
            }
        }
        final Map< String, Object > initParams = new HashMap<>();
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( opportunity.getCrmId() );
        leadSource.setEmail( contact.getEmails().get( 0 ) );
        initParams.put( LEAD, leadSource );
        if(opportunity.getRecordType().equals(RecordType.BUYER.getType())) {
        	  buyerFarmingBusinessService.updateFarmingStatus( crmOpportunityId, farmType, true );
        }
        if (farmType.equals( LONG_TERM_BUYER )) {
            triggerOppFarmingProcess( opportunity );
        }
        initParams.put( "processWebActivity", false );
        initParams.put( "buyerFarmType", "ACTIVE_BUYER" );
        runtimeService.startProcessInstanceByKey( "insideSalesFarmingProcess", initParams );
        return null;
    }

    /**
     * @param opportunity
     */
    private void triggerOppFarmingProcess( OpportunitySource opportunity ) {
        final Map< String, Object > initParams = new HashMap<>();
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( opportunity.getCrmId() );
        leadSource.setEmail( opportunity.getPrimaryContact().getEmails().get( 0 ) );
        leadSource.setFirstName( opportunity.getPrimaryContact().getFirstName() );
        leadSource.setLastName( opportunity.getPrimaryContact().getLastName() );
        leadSource.setPropertyAddress( opportunity.getPropertyAddress() );
        leadSource.setSource( opportunity.getLeadSource() );
        leadSource.setDoNotEmail( false );
        leadSource.setFarmingBuyerAction( LONG_TERM_BUYER.name() );
        initParams.put( com.owners.gravitas.constants.Constants.LEAD, leadSource );
        runtimeService.startProcessInstanceByKey( "fieldAgentFarmingProcess", initParams );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#buildTask(java.lang.
     * String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public Task buildTask( final String agentId, final String opportunityId, final Contact contact ) {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.handler.OpportunityChangeHandler#sendFeedbackEmail(
     * java.lang.String, java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public void sendFeedbackEmail( final String agentId, final String crmOpportunityId, final Contact contact ) {

    }

}
