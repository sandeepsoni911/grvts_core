package com.owners.gravitas.business.builder;

import static com.owners.gravitas.enums.LeadStatus.NEW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ContactJsonAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.ContactJsonAttributeType;
import com.owners.gravitas.enums.ProspectAttributeType;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.StringUtils;

/**
 * The Class ContactBuilderV1.
 *
 * @author shivamm
 */
@Component( "contactEntityBuilder" )
public class ContactEntityBuilder extends AbstractBuilder< LeadRequest, Contact > {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactEntityBuilder.class );

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    @Autowired
    private ContactEntityService contactServiceV1;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Contact convertTo( final LeadRequest source, final Contact destination ) {
        Contact contact = destination;
        if (source != null) {
            if (contact == null) {
                contact = new Contact();
                contact.setStage( NEW.getStatus() );
            }
            contact.setFirstName( source.getFirstName() );
            contact.setLastName( source.getLastName() );
            contact.setEmail( source.getEmail() );
            contact.setPhone( source.getPhone() );
            contact.setCompany( source.getCompany() );
            contact.setSource( source.getSource() );
            contact.setType( source.getLeadType() );
            setAllContactAttributes( source, contact );
            contact.setState( contactServiceV1.getContactAttribute( contact.getContactAttributes(),
                    ProspectAttributeType.STATE.getKey() ) );
            contact.setBuyerLeadScore( contactServiceV1.getContactAttribute( contact.getContactAttributes(),
                    ProspectAttributeType.BUYER_LEAD_SCORE.getKey() ) );
            LOGGER.debug( "lead score in contactBuilder : {} ", contact.getBuyerLeadScore() );
        }
        return contact;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    public void setAllContactAttributes( final LeadRequest source, final Contact contact ) {
        final Set< ContactAttribute > contactAttributes = CollectionUtils.isEmpty( contact.getContactAttributes() )
                ? new HashSet< ContactAttribute >()
                : contact.getContactAttributes();
        final Set< ContactJsonAttribute > contactJsonAttributes = CollectionUtils
                .isEmpty( contact.getContactJsonAttributes() ) ? new HashSet< ContactJsonAttribute >()
                        : contact.getContactJsonAttributes();

        final ObjectType objectType = objectTypeService.findByName( "lead" );
        setContactAttributes( source, contactAttributes, objectType );
        setContactJsonAttributes( contactJsonAttributes, source, objectType );

        contact.setObjectType( objectType );
        contact.setContactAttributes( contactAttributes );
        contact.setContactJsonAttributes( contactJsonAttributes );
    }

    /**
     * Sets the contact attributes.
     *
     * @param source
     *            the source
     * @param contactAttribute
     *            the contact attribute
     * @param objectType
     *            the id
     */
    private void setContactAttributes( final LeadRequest source, final Set< ContactAttribute > contactAttribute,
            final ObjectType objectType ) {

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.INTERESTED_ZIP_CODES.getKey(),
                source.getInterestedZipcodes(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.MARKETING_OPT_IN.getKey(),
                String.valueOf( source.isMarketingOptIn() ), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.LEAD_SOURCE_URL.getKey(),
                source.getLeadSourceUrl(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.WEBSITE.getKey(), source.getWebsite(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.BUYER_LEAD_QUALITY.getKey(),
                source.getBuyerLeadQuality(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PREFERRED_CONTACT_METHOD.getKey(),
                source.getPreferredContactMethod(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PREFRRED_CONTACT_TIME.getKey(),
                source.getPreferredContactTime(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PREFERRED_LANGUAGE.getKey(),
                source.getPreferredLanguage(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.OWNERS_VISITOR_ID.getKey(),
                source.getOwnersVisitorId(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.LAST_VISIT_DATE_TIME.getKey(),
                source.getLastVisitDateTime(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.SOURCE.getKey(), source.getSource(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.WORKING_WITH_REALTOR.getKey(),
                source.getWorkingWithRealtor(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.BUYER_READINESS_TIMElINE.getKey(),
                source.getBuyerReadinessTimeline(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.UNBOUNCE_PAGE_VARIANT.getKey(),
                source.getUnbouncePageVariant(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.GCL_ID.getKey(), source.getGclId(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.COMMENTS.getKey(), source.getComments(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.OWN_HOME.getKey(),
                String.valueOf( source.isOwnsHome() ), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.INTERESTED_IN_FINANCING.getKey(),
                String.valueOf( source.isInterestedInFinancing() ), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.LOAN_NUMBER.getKey(),
                String.valueOf( source.getLoanNumber() ), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.FINANCING.getKey(),
                source.getFinancing(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PRICE_RANGE.getKey(),
                source.getPriceRange(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.MEDIAN_PRICE.getKey(),
                source.getMedianPrice(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.ADDITIONAL_PROPERTY_DATA.getKey(),
                source.getAdditionalPropertyData(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.SAVED_SEARCH_VALUES.getKey(),
                source.getSavedSearchValues(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.WEBSITE_SESSION_DATA.getKey(),
                source.getWebsiteSessionData(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.REQUEST_TYPE.getKey(),
                source.getRequestType(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PRE_APPROVED_FOR_MORTGAGE.getKey(),
                source.getPreApprovedForMortgage(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.OFFER_AMOUNT.getKey(),
                source.getOfferAmount(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.EARNEST_MONEY_DEPOSIT.getKey(),
                source.getEarnestMoneyDeposit(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PURCHASE_METHOD.getKey(),
                source.getPurchaseMethod(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.DOWN_PAYMENT.getKey(),
                source.getDownPayment(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PROPERTY_TOUR_INFORMATION.getKey(),
                source.getPropertyTourInformation(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.MESSAGE.getKey(), source.getMessage(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.MLS_ID.getKey(), source.getMlsId(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.OWNERS_COM_IDENTIFIER.getKey(),
                source.getOwnersComIdentifier(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.STATE.getKey(), source.getState(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PROPERTY_ADDRESS.getKey(),
                source.getPropertyAddress(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.AL_ID.getKey(), source.getAlId(),
                objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.MLS_PACKAGE_TYPE.getKey(),
                source.getMlsPackageType(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.LISTING_CREATION_DATE.getKey(),
                source.getListingCreationDate(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.BUYER_LEAD_SCORE.getKey(),
                source.getBuyerLeadScore(), objectType );
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.BUYER_LEAD_LABEL.getKey(),
                source.getBuyerLeadLabel(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PROPERTY_CITY.getKey(),
                source.getPropertyCity(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PROPERTY_TYPE.getKey(),
                source.getPropertyType(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PROPERTY_BEDROOM.getKey(),
                source.getPropertyBedroom(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PROPERTY_BATHROOM.getKey(),
                source.getPropertyBathroom(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PROPERTY_SQUAREFOOT.getKey(),
                source.getPropertySquareFoot(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.INQUIRY_DATE.getKey(),
                source.getInquiryDate(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.SEARCH_CITY.getKey(),
                source.getSearchCity(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.SEARCH_ATTRIBUTES.getKey(),
                source.getSearchAttributes(), objectType );

        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.PARTNER_IDENTIFIER.getKey(),
                source.getPartnerIdentifier(), objectType );
        
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.LOT_SIZE.getKey(),
                source.getLotSize(), objectType );
        
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.CREDIT_SCORE.getKey(),
                source.getCreditScore(), objectType );
        
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.HOME_TYPE.getKey(),
                source.getHomeType(), objectType );
        
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.BROWSER.getKey(),
                source.getBrowser(), objectType );
        
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.OS.getKey(),
                source.getOs(), objectType );
        
        addIfNotNullContactAttributes( contactAttribute, ProspectAttributeType.DEVICE.getKey(),
                source.getDevice(), objectType );

    }

    /**
     * Adds the if not null.
     *
     * @param attributes
     *            the attributes
     * @param key
     *            the key
     * @param value
     *            the value
     * @param objectType
     *            the id
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

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    public Set< ContactJsonAttribute > convertFrom( final LeadRequest source,
            final Set< ContactJsonAttribute > destination ) {
        Set< ContactJsonAttribute > contactJsonAttribute = destination;
        if (source != null) {
            if (contactJsonAttribute == null) {
                contactJsonAttribute = new HashSet< ContactJsonAttribute >();
            }
            final ObjectType objectType = objectTypeService.findByName( "lead" );
            setContactJsonAttributes( contactJsonAttribute, source, objectType );
        }
        return contactJsonAttribute;
    }

    /**
     * Sets the contact json attributes.
     *
     * @param contactJsonAttribute
     *            the contact json attribute
     * @param source
     *            the source
     * @param crmLeadResponse
     *            the crm lead response
     * @param objectType
     *            the object type
     */
    private void setContactJsonAttributes( final Set< ContactJsonAttribute > contactJsonAttribute,
            final LeadRequest source, final ObjectType objectType ) {
        addIfNotNullContactJsonAttributes( contactJsonAttribute, ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey(),
                source.getSavedSearchValues(), objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute,
                ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey(), source.getPropertyTourInformation(),
                objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute,
                ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey(), source.getAdditionalPropertyData(),
                objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute, ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey(),
                source.getWebsiteSessionData(), objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute, ContactJsonAttributeType.ORDER_ID.getKey(),
                source.getOrderId(), objectType );
        addIfNotNullContactJsonAttributes( contactJsonAttribute, ProspectAttributeType.FARMING_BUYER_ACTIONS.getKey(),
                source.getFarmingBuyerAction(), objectType );
    }

    /**
     * Gets the json string.
     *
     * @param key
     *            the key
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @return the json string
     */
    private String convertToJsonString( final String key, final String newValue ) {
        final Map< String, List< String > > jsonMap = new HashMap< String, List< String > >();
        final List< String > jsonValueList = new ArrayList<>();
        jsonValueList.add( newValue );
        jsonMap.put( key, jsonValueList );
        return JsonUtil.toJson( jsonMap );
    }

    /**
     * Adds the if not null.
     *
     * @param attributes
     *            the attributes
     * @param key
     *            the key
     * @param value
     *            the values
     * @param objectType
     *            the object type
     */
    private void addIfNotNullContactJsonAttributes( final Set< ContactJsonAttribute > attributes, final String key,
            final String value, final ObjectType objectType ) {

        boolean flag = false;
        String currentValue = value;
        if (currentValue != null) {
            currentValue = StringUtils.subStringForLength( currentValue, 1000 );
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( final ContactJsonAttribute contactAttribute : attributes ) {
                if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                    if (contactAttribute.getValue() != null) {
                        final Map< String, List< String > > jsonMap = JsonUtil.toType( contactAttribute.getValue(),
                                Map.class );
                        final List< String > jsonValueList = jsonMap.get( key );
                        jsonValueList.add( currentValue );
                        contactAttribute.setValue( JsonUtil.toJson( jsonMap ) );
                    } else {
                        convertToJsonString( key, currentValue );
                        contactAttribute.setValue( currentValue );
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
                contactAttribute.setValue( convertToJsonString( key, currentValue ) );
                contactAttribute.setObjectAttributeConfig( config );
                attributes.add( contactAttribute );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public LeadRequest convertFrom( final Contact source, final LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
