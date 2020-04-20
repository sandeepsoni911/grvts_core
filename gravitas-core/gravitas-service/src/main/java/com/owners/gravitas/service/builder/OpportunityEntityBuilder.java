package com.owners.gravitas.service.builder;

import static com.owners.gravitas.constants.Constants.OPPORTUNITY;
import static com.owners.gravitas.enums.ProspectAttributeType.ACTUAL_CLOSING_DATE;
import static com.owners.gravitas.enums.ProspectAttributeType.BEST_TIME_TO_CONTACT;
import static com.owners.gravitas.enums.ProspectAttributeType.EXPECTED_AGENT_REVENUE;
import static com.owners.gravitas.enums.ProspectAttributeType.EXPECTED_OWNERS_COM_REVENUE;
import static com.owners.gravitas.enums.ProspectAttributeType.FIRST_TIME_HOME_BUYER;
import static com.owners.gravitas.enums.ProspectAttributeType.IS_CLIENT_EXPECTING_CALL;
import static com.owners.gravitas.enums.ProspectAttributeType.LEAD_SOURCE;
import static com.owners.gravitas.enums.ProspectAttributeType.LISTING_ID;
import static com.owners.gravitas.enums.ProspectAttributeType.SALES_PRICE;
import static com.owners.gravitas.enums.ProspectAttributeType.SELLING_HOME_AS_PART_OF_PURCHASE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ListingIdDetails;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.ListingIdDetailsService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.StringUtils;

/**
 * The Class OpportunityBuilder.
 *
 * @author shivam
 */
@Component( "opportunityEntityBuilder" )
public class OpportunityEntityBuilder {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityEntityBuilder.class );

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    /** The contact service. */
    @Autowired
    private ContactService contactService;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The Listing id details service. */
    @Autowired
    private ListingIdDetailsService listingIdDetailsService;

    /** The default response time. */
    @Value( "${opportunity.default.response.time}" )
    private Long defaultResponseTime;

    /** The Constant AGENT_OCL_NOTES. */
    private final static String AGENT_OCL_NOTES = "agentOclNotes";

    /** The Constant OCL_REFFERAL_STATUS. */
    private final static String OCL_REFFERAL_STATUS = "oclReferralStatus";

    /**
     * Convert from.
     *
     * @param destination
     *            the destination
     * @param source
     *            the opportunity
     * @param fbOpportunityId
     *            the opportunity id
     * @return the contact
     */
    public Contact convertFrom( final Contact destination, final com.owners.gravitas.domain.Opportunity source,
            final String fbOpportunityId, final Map< String, Object > updateParams ) {
        Contact contact = destination;
        final ObjectType objectType = objectTypeService.findByName( OPPORTUNITY.toLowerCase() );
        if (source != null) {
            if (contact == null) {
                contact = new Contact();
                setMandatoryVals( contact, source, objectType );
            }
            createAndUpdateContact( contact, source, objectType, updateParams );

            if (isNotBlank( fbOpportunityId )) {
                final Set< Opportunity > opportunities = contact.getOpportunities();
                if (CollectionUtils.isEmpty( opportunities )) {
                    Opportunity opportunity = null;
                    opportunity = createOrUpdateOpportunity( source, opportunity, fbOpportunityId, true );
                    opportunity.setAssignedAgentId(
                            searchService.searchByOpportunityId( fbOpportunityId ).getAgentEmail() );
                    contact.addOpportunity( opportunity );
                } else {
                    for ( final Opportunity oppEntity : opportunities ) {
                        if (oppEntity.getOpportunityId().equals( fbOpportunityId )) {
                            createOrUpdateOpportunity( source, oppEntity, fbOpportunityId, false );
                            updateOpportuntiyWithUpdateParams( oppEntity, updateParams );
                            if (isEmpty( oppEntity.getAssignedAgentId() )) {
                                oppEntity.setAssignedAgentId(
                                        searchService.searchByOpportunityId( fbOpportunityId ).getAgentEmail() );
                            }
                        }
                    }
                }
            }
        }
        return contact;
    }

    /**
     * Convert from.
     *
     * @param destination
     *            the destination
     * @param opportunity
     *            the opportunity
     * @param opportunityId
     *            the opportunity id
     * @param agentEmail
     *            the agent email
     * @return the contact
     */
    public Contact convertFrom( final Contact destination, final com.owners.gravitas.domain.Opportunity opportunity,
            final String opportunityId, final String agentEmail ) {
        Contact contact = destination;
        final ObjectType objectType = objectTypeService.findByName( OPPORTUNITY.toLowerCase() );
        if (contact == null) {
            contact = new Contact();
            setMandatoryVals( contact, opportunity, objectType );
        }
        createAndUpdateContact( contact, opportunity, objectType, null );
        final Set< Opportunity > setOpportunityV1 = contact.getOpportunities();
        final Opportunity opportunityV1 = new Opportunity();
        createOrUpdateOpportunity( opportunity, opportunityV1, opportunityId, true );
        opportunityV1.setAssignedAgentId( agentEmail );
        opportunityV1.setContact( contact );
        opportunityV1.setAgentOclNotes(
                org.apache.commons.lang3.StringUtils.substring( opportunity.getAgentOclNotes(), 0, 2000 ) );
        opportunityV1.setOclReferralStatus( opportunity.getOclReferralStatus() );
        setOpportunityV1.add( opportunityV1 );
        contact.setOpportunities( setOpportunityV1 );
        return contact;
    }

    /**
     * Sets the contact attributes.
     *
     * @param opportunity
     *            the opportunity
     * @param contactAttributes
     *            the contact attributes
     * @param objectType
     *            the object type
     */
    private Set< ContactAttribute > setContactAttributes( final com.owners.gravitas.domain.Opportunity opportunity,
            final Set< ContactAttribute > contactAttributes, final ObjectType objectType,
            final Map< String, Object > updateParams ) {
        addIfNotNullContactAttributes( contactAttributes, ACTUAL_CLOSING_DATE.getKey(),
                DateUtil.toString( opportunity.getActualClosingDate(), DateUtil.DEFAULT_CRM_DATE_PATTERN ),
                objectType );
        addIfNotNullContactAttributes( contactAttributes, EXPECTED_AGENT_REVENUE.getKey(),
                String.valueOf( opportunity.getExpectedAgentRevenue() ), objectType );
        addIfNotNullContactAttributes( contactAttributes, EXPECTED_OWNERS_COM_REVENUE.getKey(),
                String.valueOf( opportunity.getExpectedOwnersComRevenue() ), objectType );
        addIfNotNullContactAttributes( contactAttributes, SALES_PRICE.getKey(),
                String.valueOf( opportunity.getSalesPrice() ), objectType );
        addIfNotNullContactAttributes( contactAttributes, LEAD_SOURCE.getKey(), opportunity.getLeadSource(),
                objectType );
        if (CollectionUtils.isNotEmpty( opportunity.getListingIds() )) {
            addIfNotNullContactAttributes( contactAttributes, LISTING_ID.getKey(), opportunity.getListingIds().get( 0 ),
                    objectType );
        }

        // add additional params to contactAttributes
        if (!org.springframework.util.CollectionUtils.isEmpty( updateParams )) {
            addIfNotNullContactAttributes( contactAttributes, BEST_TIME_TO_CONTACT.getKey(),
                    StringUtils.stringValueNotEmtpy( updateParams.get( BEST_TIME_TO_CONTACT.getKey() ) ), objectType );
            addIfNotNullContactAttributes( contactAttributes, IS_CLIENT_EXPECTING_CALL.getKey(),
                    StringUtils.stringValueNotEmtpy( updateParams.get( IS_CLIENT_EXPECTING_CALL.getKey() ) ),
                    objectType );
            addIfNotNullContactAttributes( contactAttributes, FIRST_TIME_HOME_BUYER.getKey(),
                    StringUtils.stringValueNotEmtpy( updateParams.get( FIRST_TIME_HOME_BUYER.getKey() ) ), objectType );
            addIfNotNullContactAttributes( contactAttributes, SELLING_HOME_AS_PART_OF_PURCHASE.getKey(),
                    StringUtils.stringValueNotEmtpy( updateParams.get( SELLING_HOME_AS_PART_OF_PURCHASE.getKey() ) ),
                    objectType );
        }
        return contactAttributes;
    }

    /**
     * Adds the if not null contact attributes.
     *
     * @param attributes
     *            the attributes
     * @param key
     *            the key
     * @param value
     *            the value
     * @param objectType
     *            the object type
     */
    private void addIfNotNullContactAttributes( final Set< ContactAttribute > attributes, final String key,
            final String value, final ObjectType objectType ) {
        boolean flag = false;
        String currentValue = value;
        if (currentValue != null) {
            currentValue = StringUtils.subStringForLength( currentValue, 1000 );
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( final ContactAttribute contactAttribute : attributes ) {
                if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                    contactAttribute.setValue( currentValue );
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                final ContactAttribute contactAttribute = new ContactAttribute();
                contactAttribute.setValue( currentValue );
                contactAttribute.setObjectAttributeConfig( config );
                attributes.add( contactAttribute );
            }
        }
    }

    /**
     * Update opportuntiy.
     *
     * @param oppEntity
     *            the opp entity
     * @param updateParams
     *            the update params
     */
    private void updateOpportuntiyWithUpdateParams( final Opportunity oppEntity,
            final Map< String, Object > updateParams ) {
        if (!org.springframework.util.CollectionUtils.isEmpty( updateParams )) {
            if (updateParams.get( AGENT_OCL_NOTES ) != null) {
                oppEntity.setAgentOclNotes( org.apache.commons.lang3.StringUtils
                        .substring( updateParams.get( AGENT_OCL_NOTES ).toString(), 0, 2000 ) );
            }
            if (updateParams.get( OCL_REFFERAL_STATUS ) != null) {
                oppEntity.setOclReferralStatus( updateParams.get( OCL_REFFERAL_STATUS ).toString() );
            }
        }
    }

    /**
     * Creates the or update opportunity.
     *
     * @param opportunity
     *            the opportunity
     * @param oppEntity
     *            the opp entity
     * @param fbOpportunityId
     *            the fb opportunity id
     * @param isNew
     *            the is new
     */
    private Opportunity createOrUpdateOpportunity( final com.owners.gravitas.domain.Opportunity opportunity,
            Opportunity oppEntity, final String fbOpportunityId, final boolean isNew ) {
        if (oppEntity == null) {
            oppEntity = new Opportunity();
        } else {
            // delete existing listings in case of UPDATE ONLY
            listingIdDetailsService.delete( oppEntity.getId() );
        }
        if (CollectionUtils.isNotEmpty( opportunity.getListingIds() )) {
            setListingIds( opportunity.getListingIds(), oppEntity );
        }
        oppEntity.setOpportunityId( fbOpportunityId );
        final boolean deleted = opportunity.getDeleted() == null ? true : opportunity.getDeleted();
        oppEntity.setDeleted( deleted );
        oppEntity.setAssignedDate( DateUtil.toDate( opportunity.getAssignedDtm() ) );

        setAgentPerformanceFields( opportunity, oppEntity, isNew );
        return oppEntity;
    }

    /**
     * Sets the listing ids.
     *
     * @param listingIds
     *            the listing ids
     * @param opportunity
     *            the opportunity
     */
    private void setListingIds( final List< String > listingIds, final Opportunity opportunity ) {
        final Set< ListingIdDetails > listingIdDetailsSet = new HashSet<>();
        for ( final String listingId : listingIds ) {
            final ListingIdDetails listingIdDetails = new ListingIdDetails();
            listingIdDetails.setListingId( listingId );
            listingIdDetailsSet.add( listingIdDetails );
        }
        opportunity.setListingIdDetails( listingIdDetailsSet );
    }

    /**
     * Sets the agent performance fields.
     *
     * @param opportunity
     *            the opportunity
     * @param oppEntity
     *            the opp entity
     * @param isNew
     *            the is new
     */
    private void setAgentPerformanceFields( final com.owners.gravitas.domain.Opportunity opportunity,
            final Opportunity oppEntity, final boolean isNew ) {
        final boolean isEligible = null != opportunity.getAssignedDtm() && oppEntity.getResponseTime() == null
                && !opportunity.getStage().equalsIgnoreCase( BuyerStage.NEW.getStage() )
                && !opportunity.getStage().equalsIgnoreCase( BuyerStage.CLAIMED.getStage() );
        if (isEligible) {
            if (isNew) {
                oppEntity
                        .setFirstContactDtm( new DateTime( opportunity.getAssignedDtm() ).plus( defaultResponseTime ) );
            } else {
                oppEntity.setFirstContactDtm( new DateTime( Instant.now().toEpochMilli() ) );
            }
            final Long responseTime = oppEntity.getFirstContactDtm().getMillis() - opportunity.getAssignedDtm();
            oppEntity.setResponseTime( responseTime );
        }
    }

    /**
     * Contact configuration.
     *
     * @param contact
     *            the contact
     * @param opportunity
     *            the opportunity
     */
    private void createAndUpdateContact( final Contact contact,
            final com.owners.gravitas.domain.Opportunity opportunity, final ObjectType objectType,
            final Map< String, Object > updateParams ) {
        contact.setObjectType( objectType );
        Set< ContactAttribute > contactAttributes = contact.getContactAttributes();
        if (contactAttributes == null) {
            contactAttributes = new HashSet<>();
            contact.setContactAttributes( contactAttributes );
        }
        setContactAttributes( opportunity, contactAttributes, objectType, updateParams );
        contact.setSource( opportunity.getLeadSource() );
        contact.setStage( opportunity.getStage() );
        contact.setType( opportunity.getOpportunityType() );
        contact.setCrmId( opportunity.getCrmId() );
    }

    /**
     * Sets the mandatory vals.
     *
     * @param contact
     *            the contact
     * @param opportunity
     *            the opportunity
     * @param objectType
     *            the object type
     */
    private void setMandatoryVals( final Contact contact, final com.owners.gravitas.domain.Opportunity opportunity,
            final ObjectType objectType ) {
        final String contactId = contactService.findContactIdByOpportunityId( opportunity.getCrmId() );
        final Map< String, Object > map = contactService.findContactById( contactId, "contactId" );
        final Map< String, Object > map1 = ( Map< String, Object > ) map.get( "Contact" );
        contact.setEmail( addIfNotNull( map1.get( "Email" ) ) );
        contact.setLastName( addIfNotNull( map1.get( "LastName" ) ) );
        contact.setFirstName( addIfNotNull( map1.get( "FirstName" ) ) );
        contact.setPhone( addIfNotNull( map1.get( "Phone" ) ) );
    }

    /**
     * Adds the if not null.
     *
     * @param value
     *            the value
     * @return the string
     */
    private String addIfNotNull( final Object value ) {
        return value != null ? value.toString() : EMPTY;
    }

    /**
     * Calculate response time.
     *
     * @param firstContactDtm
     *            the first contact dtm
     * @param assignedDtm
     *            the assigned dtm
     * @return the long
     */
    private Long calculateResponseTime( final Long firstContactDtm, final Long assignedDtm ) {
        Long responseTime = null;
        if (null != assignedDtm) {
            if (firstContactDtm != null) {
                responseTime = firstContactDtm - assignedDtm;
            }
        }
        return responseTime;
    }

}
