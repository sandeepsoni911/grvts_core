package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_EMAIL;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.BLANK;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.constants.Constants.HYPHEN;
import static com.owners.gravitas.constants.Constants.MILLION_VAL;
import static com.owners.gravitas.constants.Constants.OWNERS_ID;
import static com.owners.gravitas.constants.Constants.PLUS_SIGN;
import static com.owners.gravitas.constants.Constants.SPACE;
import static com.owners.gravitas.constants.Constants.THOUSAND_CONSTANT;
import static com.owners.gravitas.constants.Constants.THOUSAND_VAL;
import static com.owners.gravitas.enums.ActionEntity.OPPORTUNITY;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.UPDATE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.machinelearning.AmazonMachineLearning;
import com.amazonaws.services.machinelearning.model.PredictRequest;
import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.dao.AgentOpportunityDao;
import com.owners.gravitas.dao.OpportunityActionDao;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.domain.entity.RegisteredUser;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.dto.response.RegisteredUserResponse;
import com.owners.gravitas.dto.response.RegistrationResponse;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.AgentOpportunityNotFoundException;
import com.owners.gravitas.repository.OpportunityActionRepository;
import com.owners.gravitas.repository.RegisteredUserRepository;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.builder.OpportunityEntityBuilder;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class AgentOpportunityServiceImpl.
 *
 * @author harshads
 */
@Service
public class AgentOpportunityServiceImpl implements AgentOpportunityService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentOpportunityServiceImpl.class );

    /** The opportunity dao. */
    @Autowired
    private AgentOpportunityDao opportunityDao;

    /** The amc client. */
    @Autowired
    private AmazonMachineLearning amlClient;

    /** The aws endpoint. */
    @Value( value = "${aws.predict.endpoint}" )
    private String awsEndpoint;

    /** The ml model id. */
    @Value( value = "${aws.machine.learning.modelId}" )
    private String mlModelId;

    /*    *//** The entity opportunity builder. */
    /*
     * @Autowired
     * private OpportunityBuilder entityOpportunityBuilder;
     */

    /** The entity opportunity builder V 1. */
    @Autowired
    private OpportunityEntityBuilder entityOpportunityBuilderV1;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The Constant obj. */
    private final static Map< String, String > updateFieldMapping = new HashMap<>();

    /** The agent error slack channel. */
    @Value( "${slack.channel.url.agents.errors}" )
    private String agentErrorSlackChannel;

    /** The registered user repository. */
    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    /** The opportunity action dao. */
    @Autowired
    private OpportunityActionDao opportunityActionDao;

    /** The opportunity action repository. */
    @Autowired
    private OpportunityActionRepository opportunityActionRepository;

    /** The buyer service. */
    @Autowired
    private BuyerService buyerService;

    /** The buyer farming config. */
    @Autowired
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The agent contact service. */
    @Autowired
    private AgentContactService agentContactService;

    /**
     * Initialize opportunity fields map.
     */
    @PostConstruct
    private void initializeOpportunityFieldsMap() {
        // key is domain property name & values is entity property name
        updateFieldMapping.put( "leadSource", "leadSource" );
        updateFieldMapping.put( "deleted", "deleted" );
        updateFieldMapping.put( "opporunityType", "type" );
    }

    /**
     * Gets the opportunity by id.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity by id
     */
    @Override
    public Opportunity getOpportunityById( final String agentId, final String opportunityId ) {
        final Opportunity opportunity = opportunityDao.getOpportunityById( agentId, opportunityId );
        if (opportunity == null) {
            throw new AgentOpportunityNotFoundException(
                    "Agent opportunity not found for agentId: " + agentId + " opportunityId :" + opportunityId );
        }
        return opportunity;
    }

    /**
     * Save opportunity.
     *
     * @param agentId
     *            the agent id
     * @param agentEmail
     *            the agent email
     * @param opportunity
     *            the opportunity
     * @param contactEmail
     *            the contact email
     * @return the post response
     */
    @Override
    @Audit( type = CREATE, entity = OPPORTUNITY, args = { AGENT_ID, AGENT_EMAIL, ACTION_OBJ, "contactEmail" } )
    @Transactional( propagation = REQUIRED )
    public PostResponse saveOpportunity( final String agentId, final String agentEmail, final Opportunity opportunity,
            final String contactEmail ) {
        String ownersId = null;
        com.owners.gravitas.domain.entity.Contact contact = getContactFromDb( opportunity, contactEmail );
        final PostResponse opporunityRes = opportunityDao.saveOpportunity( agentId, opportunity );
        if (opportunity.getOpportunityType().equals( RecordType.BUYER.getType() )) {
            ownersId = getOwnersId( contact );
        }
        if (StringUtils.isNotBlank( ownersId )) {
            final Map< String, Object > contactRequest = new HashMap<>();
            contactRequest.put( OWNERS_ID, ownersId );
            agentContactService.patchContact( agentId, opportunity.getContacts().toArray()[0].toString(),
                    contactRequest );
        }
        /*
         * final com.owners.gravitas.domain.entity.Opportunity opportunityEntity
         * = entityOpportunityBuilder
         * .convertFrom( opportunity );
         * opportunityEntity.setOpportunityId( opporunityRes.getName() );
         * opportunityEntity.setAssignedAgentId( agentEmail );
         * opportunityService.save( opportunityEntity );
         */

        // db update code changes
        contact = entityOpportunityBuilderV1.convertFrom( contact, opportunity, opporunityRes.getName(), agentEmail );
        contactServiceV1.save( contact );
        return opporunityRes;
    }

    /**
     * Gets the contact from db.
     *
     * @param opportunity
     *            the opportunity
     * @param contactEmail
     *            the contact email
     * @return the contact from db
     */
    private com.owners.gravitas.domain.entity.Contact getContactFromDb( final Opportunity opportunity,
            final String contactEmail ) {
        com.owners.gravitas.domain.entity.Contact contact = contactServiceV1.findByCrmId( opportunity.getCrmId() );
        if (contact == null) {
            contact = contactServiceV1.getContact( contactEmail, opportunity.getOpportunityType() );
        }
        return contact;
    }

    /**
     * Update opportunity on firebase and in mysql db.
     *
     * @param agentId
     *            the agent id
     * @param agentEmail
     *            the agent email
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     */
    /*
     * @Override
     * @Audit( type = UPDATE, entity = OPPORTUNITY, args = { AGENT_ID,
     * AGENT_EMAIL, ENTITY_ID, ACTION_OBJ } )
     * @Transactional( propagation = REQUIRED )
     * public void updateOpportunity( final String agentId, final String
     * agentEmail, final String opportunityId,
     * final Opportunity opportunity ) {
     * com.owners.gravitas.domain.entity.Opportunity opportunityEntity =
     * opportunityService
     * .getOpportunityByOpportunityId( opportunityId );
     * opportunityEntity = entityOpportunityBuilder.convertFrom( opportunity,
     * opportunityEntity );
     * opportunityEntity.setOpportunityId( opportunityId );
     * opportunityEntity.setAssignedAgentId( agentEmail );
     * opportunityService.save( opportunityEntity );
     * opportunityDao.updateOpportunity( agentId, opportunityId, opportunity );
     * }
     */

    /**
     * Save opportunity stage.
     *
     * @param agentId
     *            the agent id
     * @param fbOpportunityId
     *            the fb opportunity id
     * @param updateParams
     *            the opportunity
     * @param opportunity
     *            the opportunity
     * @param contactEmail
     *            the contact email
     * @return the agent response
     */
    @Override
    @Audit( type = UPDATE, entity = OPPORTUNITY, args = { AGENT_ID, ENTITY_ID, ACTION_OBJ, "opportunity",
            "contactEmail" } )
    @Transactional( propagation = REQUIRED )
    public Opportunity patchOpportunity( final String agentId, final String fbOpportunityId,
            final Map< String, Object > updateParams, final Opportunity opportunity, final String contactEmail ) {

        if (null != updateParams.get( "deleted" ) && ( boolean ) updateParams.get( "deleted" )) {
            opportunity.getActionFlowIds().forEach( actionFlow -> {
                final List< OpportunityAction > actionFlowEntry = opportunityActionRepository
                        .findByActionFlowId( actionFlow );
                actionFlowEntry.forEach( action -> action.setDeleted( true ) );
                opportunityActionRepository.save( actionFlowEntry );
            } );
        }

        com.owners.gravitas.domain.entity.Contact contact = getContactFromDb( opportunity, contactEmail );
        contact = entityOpportunityBuilderV1.convertFrom( contact, opportunity, fbOpportunityId, updateParams );
        
        contactServiceV1.save( contact );
        LOGGER.info( "Updated in GR_OPPORTUNITY" );
        return opportunityDao.patchOpportunity( agentId, fbOpportunityId, updateParams );
    }

    /**
     * Gets the agent new opportunities count.
     *
     * @param agentId
     *            the agent id
     * @return the agent new opportunities count
     */
    @Override
    public int getAgentNewOpportunitiesCount( final String agentId ) {
        return opportunityDao.getAgentNewOpportunitiesCount( agentId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentOpportunityService#
     * getAgentClaimedOpportunitiesWithOpenTaskCount(java.lang.String)
     */
    @Override
    public boolean hasAgentClaimedOpportunityWithOpenTasks( final String agentId ) {
        return opportunityDao.hasAgentClaimedOpportunityWithOpenTasks( agentId );
    }

    /**
     * Gets the opportunity score.
     *
     * @param records
     *            the records
     * @return the opportunity score
     */
    @Override
    public PredictResult getOpportunityScore( final Map< String, String > records ) {
        final PredictRequest request = new PredictRequest().withMLModelId( mlModelId )
                .withPredictEndpoint( awsEndpoint ).withRecord( records );
        return amlClient.predict( request );
    }

    /**
     * Does agent has opportunities beyond limit.
     *
     * @param agentId
     *            the agent id
     * @param fromDtm
     *            the from dtm is dtm of before 7 days.
     * @return the int
     */
    @Override
    public int getOpportunityCountByDays( final String agentId, final long fromDtm ) {
        return opportunityDao.getOpportunityCountByDays( agentId, fromDtm );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentOpportunityService#
     * hasIncompleteActionFlow(java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public boolean hasIncompleteActionFlow( final String agentEmail ) {
        final Integer incompleteActionFlowCount = Integer
                .valueOf( opportunityActionDao.getIncompleteActionFlowCount( agentEmail ).toString() );
        LOGGER.info(
                "agent with email as " + agentEmail + " has " + incompleteActionFlowCount + " incomplete actions" );
        return incompleteActionFlowCount > 0;
    }

    /**
     * Gets the client id.
     *
     * @param contact
     *            the contact
     * @return the client id
     */
    private String getOwnersId( final Contact contact ) {
        String ownersId = null;
        if (null != contact) {
            ownersId = contact.getOwnersComId();
            if (StringUtils.isBlank( ownersId )) {
                LOGGER.debug( " contact is not registered yet, lets look into mapping table" );
                final RegisteredUser registeredUser = registeredUserRepository.findByEmail( contact.getEmail() );
                if (registeredUser != null) {
                    LOGGER.debug( " mapping available!!, isRegistered" );
                    ownersId = registeredUser.getUserId();
                } else {
                    final Map< String, Object > registrationParams = new HashMap<>();
                    registrationParams.put( "firstName", contact.getFirstName() );
                    registrationParams.put( "lastName", contact.getLastName() );
                    registrationParams.put( "emailAddress", contact.getEmail() );
                    final ContactAttribute conAttr = contact.getContactAttributes().stream()
                            .filter( attr -> attr.getObjectAttributeConfig().getAttributeName().equals( "state" ) )
                            .findFirst().orElse( null );
                    final String state = conAttr != null ? conAttr.getValue() : BLANK;
                    try {
                        if (buyerFarmingConfig.isBuyerFarmingEnabled()
                                && buyerFarmingConfig.isBuyerAutoRegistrationEnabled()
                                && buyerService.isBuyerAutoRegistrationEmail( contact.getEmail() )
                                && buyerService.isFarmLongTermState( state )) {
                            LOGGER.debug( " buyer farming and registration is enabled, lets register" );
                            final RegistrationResponse registrationResponse = buyerService
                                    .registerBuyer( registrationParams );
                            LOGGER.info(
                                    "Buyer auto registration response: " + JsonUtil.toJson( registrationResponse ) );
                            final RegisteredUserResponse response = registrationResponse.getResult();
                            ownersId = ( response.getUser() != null ) ? response.getUser().getUserId() : null;
                        }
                    } catch ( final Exception e ) {
                        LOGGER.info( "registration of opportunity!! something went wrong in registration of :"
                                + contact.getEmail() + ": " + e.getMessage() );
                    }
                    contact.setOwnersComId( ownersId );
                }
                LOGGER.debug( " client id is : " + ownersId );
            }
        }
        return ownersId;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentOpportunityService#patchOpportunity(java
     * .lang.String, java.lang.String, java.lang.String, java.util.Map)
     */
    @Audit( type = UPDATE, entity = OPPORTUNITY, args = { AGENT_ID, AGENT_EMAIL, ENTITY_ID, ACTION_OBJ } )
    @Override
    public void patchOpportunity( final String agentId, final String agentEmail, final String opportunityId,
            final Map< String, Object > opportunityData ) {
        opportunityDao.patchOpportunity( agentId, opportunityId, opportunityData );
    }

    /**
     * Sets the first contact dtm.
     *
     * @param opportunity
     *            the opportunity
     */
    private boolean isUpdateRequiredForAgentPerformance( final com.owners.gravitas.domain.Opportunity fbOpportunity ) {
        return ( null == fbOpportunity.getFirstContactDtm()
                && ( fbOpportunity.getOpportunityType().equalsIgnoreCase( RecordType.BUYER.getType() )
                        || fbOpportunity.getOpportunityType().equalsIgnoreCase( RecordType.SELLER.getType() ) )
                && ( fbOpportunity.getStage().equalsIgnoreCase( BuyerStage.NEW.getStage() )
                        || fbOpportunity.getStage().equalsIgnoreCase( BuyerStage.CLAIMED.getStage() ) ) );
    }

    @Override
    @Audit( type = UPDATE, entity = OPPORTUNITY, args = { AGENT_ID, ENTITY_ID, ACTION_OBJ, "opportunity",
            "contactEmail" } )
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public Opportunity patchOpportunityForCTA( final String agentId, final String fbOpportunityId,
            final Opportunity opportunity, final String contactEmail ) {
        final Map< String, Object > updateParams = new HashMap<>();

        final com.owners.gravitas.domain.entity.Contact contact = getContactFromDb( opportunity, contactEmail );
        if (isUpdateRequiredForAgentPerformance( opportunity )) {
            final DateTime firstContactDtm = new DateTime( Instant.now().toEpochMilli() );
            final Long responseTime = firstContactDtm.minus( opportunity.getAssignedDtm() ).getMillis();

            final Map< String, Object > dummyNullMap = null;
            entityOpportunityBuilderV1.convertFrom( contact, opportunity, fbOpportunityId, dummyNullMap );

            final com.owners.gravitas.domain.entity.Opportunity oppV1 = contact.getOpportunities().stream()
                    .filter( opp -> fbOpportunityId.equals( opp.getOpportunityId() ) ).findFirst().get();
            if (null != oppV1) {
                oppV1.setFirstContactDtm( firstContactDtm );
                oppV1.setResponseTime( responseTime );
                updateParams.put( "firstContactDtm", oppV1.getFirstContactDtm().getMillis() );
            }
        }
        contactServiceV1.save( contact );
        LOGGER.info( "Updated in GR_OPPORTUNITY" );
        return opportunityDao.patchOpportunity( agentId, fbOpportunityId, updateParams );
    }

    /**
     * Gets the top price.
     *
     * @param priceRange
     *            the price range
     * @return the top price
     */
    @Override
    public int getTopPrice( final String priceRange ) {
        LOGGER.info( "getTopPrice calculation top price for priceRange : {} ", priceRange );
        int topPrice = 0;
        String topPriceStr = EMPTY;
        if (priceRange.contains( HYPHEN )) {
            topPriceStr = priceRange.substring( priceRange.lastIndexOf( SPACE ) + 1 );
        } else if (priceRange.contains( PLUS_SIGN )) {
            topPriceStr = priceRange.substring( 0, 2 );
        }
        if (isNotBlank( topPriceStr )) {
            if (topPriceStr.contains( THOUSAND_CONSTANT )) {
                topPriceStr = topPriceStr.substring( 0, topPriceStr.length() - 1 );
                topPrice = Integer.parseInt( topPriceStr ) * THOUSAND_VAL;
            } else {
                topPriceStr = topPriceStr.substring( 0, topPriceStr.length() - 1 );
                topPrice = Integer.parseInt( topPriceStr ) * MILLION_VAL;
            }
        }
        return topPrice;
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.service.AgentOpportunityService#patchOpportunityStage(java.lang.String, java.lang.String, java.lang.String, com.owners.gravitas.domain.Opportunity)
     */
    @Override
    public Opportunity patchOpportunityStage( final String agentId, final String opportunityId, final String stageToSet,
            final Opportunity opportunity ) {

        final Map< String, Object > fbOpporutnityMap = new HashMap< String, Object >();
        final List< Stage > stageHistory = opportunity.getStageHistory();
        final Stage stage = new Stage();
        final long timestamp = Instant.now().toEpochMilli();
        stage.setStage( stageToSet );
        stage.setTimestamp( timestamp );
        stageHistory.add( stage );
        fbOpporutnityMap.put( "stageHistory", stageHistory );
        fbOpporutnityMap.put( "stage", stageToSet );
        fbOpporutnityMap.put( "lastModifiedDtm", new Date().getTime() );
        return opportunityDao.patchOpportunity( agentId, opportunityId, fbOpporutnityMap );
    }
    
}
