package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttributeLog;
import com.owners.gravitas.domain.entity.ContactJsonAttributeLog;
import com.owners.gravitas.domain.entity.ContactLog;
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

@Component( "contactLogBuilder" )
public class ContactLogBuilder extends AbstractBuilder< LeadRequest, ContactLog > {

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    @Override
    public ContactLog convertTo( LeadRequest source, ContactLog destination ) {
        ContactLog contactLog = destination;
        if (source != null) {
            if (contactLog == null) {
                contactLog = new ContactLog();
            }
            contactLog.setFirstName( source.getFirstName() );
            contactLog.setLastName( source.getLastName() );
            contactLog.setEmail( source.getEmail() );
            contactLog.setPhone( source.getPhone() );
            contactLog.setCompany( source.getCompany() );
            contactLog.setSource( source.getSource() );
            contactLog.setType( source.getLeadType() );
            contactLog.setState( source.getState() ); 

            setAllContactAttributes( source, contactLog );

            Contact contact = contactServiceV1.getContact( source.getEmail(), source.getLeadType() );
            if (null != contact) {
                contactLog.setContactId( contact.getId() );
                contactLog.setStage( contact.getStage() );
                contactLog.setBuyerLeadScore( contact.getBuyerLeadScore() );
                contactLog.setCrmId( contact.getCrmId() );
                contactLog.setOwnersComId( contact.getOwnersComId() ); 
            }
        }
        return contactLog;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    public void setAllContactAttributes( final LeadRequest source, final ContactLog contactLog ) {
        final Set< ContactAttributeLog > contactAttributeLogs = CollectionUtils
                .isEmpty( contactLog.getContactAttributeLogs() ) ? new HashSet< ContactAttributeLog >()
                        : contactLog.getContactAttributeLogs();
        final Set< ContactJsonAttributeLog > contactJsonAttributeLogs = CollectionUtils
                .isEmpty( contactLog.getContactJsonAttributeLogs() ) ? new HashSet< ContactJsonAttributeLog >()
                        : contactLog.getContactJsonAttributeLogs();

        final ObjectType objectType = objectTypeService.findByName( "lead" );
        setContactAttributeLogs( source, contactAttributeLogs, objectType );
        setContactJsonAttributeLogs( contactJsonAttributeLogs, source, objectType );

        contactLog.setObjectType( objectType );
        contactLog.setContactAttributeLogs( contactAttributeLogs );
        contactLog.setContactJsonAttributeLogs( contactJsonAttributeLogs );
    }

    /**
     * Sets the contact attributes.
     *
     * @param source
     *            the source
     * @param contactAttributeLogs
     *            the contact log attribute
     * @param objectType
     *            the id
     */
    private void setContactAttributeLogs( final LeadRequest source,
            final Set< ContactAttributeLog > contactAttributeLogs, final ObjectType objectType ) {

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.INTERESTED_ZIP_CODES.getKey(),
                source.getInterestedZipcodes(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.MARKETING_OPT_IN.getKey(),
                String.valueOf( source.isMarketingOptIn() ), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.LEAD_SOURCE_URL.getKey(),
                source.getLeadSourceUrl(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.WEBSITE.getKey(),
                source.getWebsite(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.BUYER_LEAD_QUALITY.getKey(),
                source.getBuyerLeadQuality(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PREFERRED_CONTACT_METHOD.getKey(),
                source.getPreferredContactMethod(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PREFRRED_CONTACT_TIME.getKey(),
                source.getPreferredContactTime(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PREFERRED_LANGUAGE.getKey(),
                source.getPreferredLanguage(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.OWNERS_VISITOR_ID.getKey(),
                source.getOwnersVisitorId(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.LAST_VISIT_DATE_TIME.getKey(),
                source.getLastVisitDateTime(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.SOURCE.getKey(),
                source.getSource(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.WORKING_WITH_REALTOR.getKey(),
                source.getWorkingWithRealtor(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.BUYER_READINESS_TIMElINE.getKey(),
                source.getBuyerReadinessTimeline(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.UNBOUNCE_PAGE_VARIANT.getKey(),
                source.getUnbouncePageVariant(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.GCL_ID.getKey(),
                source.getGclId(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.COMMENTS.getKey(),
                source.getComments(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.OWN_HOME.getKey(),
                String.valueOf( source.isOwnsHome() ), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.INTERESTED_IN_FINANCING.getKey(),
                String.valueOf( source.isInterestedInFinancing() ), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.LOAN_NUMBER.getKey(),
                String.valueOf( source.getLoanNumber() ), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.FINANCING.getKey(),
                source.getFinancing(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PRICE_RANGE.getKey(),
                source.getPriceRange(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.MEDIAN_PRICE.getKey(),
                source.getMedianPrice(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.ADDITIONAL_PROPERTY_DATA.getKey(),
                source.getAdditionalPropertyData(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.SAVED_SEARCH_VALUES.getKey(),
                source.getSavedSearchValues(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.WEBSITE_SESSION_DATA.getKey(),
                source.getWebsiteSessionData(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.REQUEST_TYPE.getKey(),
                source.getRequestType(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs,
                ProspectAttributeType.PRE_APPROVED_FOR_MORTGAGE.getKey(), source.getPreApprovedForMortgage(),
                objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.OFFER_AMOUNT.getKey(),
                source.getOfferAmount(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.EARNEST_MONEY_DEPOSIT.getKey(),
                source.getEarnestMoneyDeposit(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PURCHASE_METHOD.getKey(),
                source.getPurchaseMethod(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.DOWN_PAYMENT.getKey(),
                source.getDownPayment(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs,
                ProspectAttributeType.PROPERTY_TOUR_INFORMATION.getKey(), source.getPropertyTourInformation(),
                objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.MESSAGE.getKey(),
                source.getMessage(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.MLS_ID.getKey(),
                source.getMlsId(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.OWNERS_COM_IDENTIFIER.getKey(),
                source.getOwnersComIdentifier(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.STATE.getKey(), source.getState(),
                objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PROPERTY_ADDRESS.getKey(),
                source.getPropertyAddress(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.AL_ID.getKey(), source.getAlId(),
                objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.MLS_PACKAGE_TYPE.getKey(),
                source.getMlsPackageType(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.LISTING_CREATION_DATE.getKey(),
                source.getListingCreationDate(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.BUYER_LEAD_SCORE.getKey(),
                source.getBuyerLeadScore(), objectType );
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.BUYER_LEAD_LABEL.getKey(),
                source.getBuyerLeadLabel(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PROPERTY_CITY.getKey(),
                source.getPropertyCity(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PROPERTY_TYPE.getKey(),
                source.getPropertyType(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PROPERTY_BEDROOM.getKey(),
                source.getPropertyBedroom(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PROPERTY_BATHROOM.getKey(),
                source.getPropertyBathroom(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PROPERTY_SQUAREFOOT.getKey(),
                source.getPropertySquareFoot(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.INQUIRY_DATE.getKey(),
                source.getInquiryDate(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.SEARCH_CITY.getKey(),
                source.getSearchCity(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.SEARCH_ATTRIBUTES.getKey(),
                source.getSearchAttributes(), objectType );

        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.PARTNER_IDENTIFIER.getKey(),
                source.getPartnerIdentifier(), objectType );
        
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.LOT_SIZE.getKey(),
                source.getLotSize(), objectType );
        
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.CREDIT_SCORE.getKey(),
                source.getCreditScore(), objectType );
        
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.HOME_TYPE.getKey(),
                source.getHomeType(), objectType );
        
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.BROWSER.getKey(),
                source.getBrowser(), objectType );
        
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.OS.getKey(),
                source.getOs(), objectType );
        
        addIfNotNullContactAttributeLogs( contactAttributeLogs, ProspectAttributeType.DEVICE.getKey(),
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
    private void addIfNotNullContactAttributeLogs( final Set< ContactAttributeLog > attributes, final String key,
            final String value, final ObjectType objectType ) {
        boolean flag = false;
        String currentValue = value;
        if (currentValue != null) {
            currentValue = StringUtils.subStringForLength( currentValue, 1000 );
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( final ContactAttributeLog contactAttributeLog : attributes ) {
                if (contactAttributeLog.getObjectAttributeConfig().equals( config )) {
                    contactAttributeLog.setValue( currentValue );
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                final ContactAttributeLog contactAttributeLog = new ContactAttributeLog();
                contactAttributeLog.setValue( currentValue );
                contactAttributeLog.setObjectAttributeConfig( config );
                attributes.add( contactAttributeLog );
            }
        }
    }

    /**
     * Sets the contact json attributes.
     *
     * @param contactJsonAttributeLogs
     *            the contact log json attribute
     * @param source
     *            the source
     * @param crmLeadResponse
     *            the crm lead response
     * @param objectType
     *            the object type
     */
    private void setContactJsonAttributeLogs( final Set< ContactJsonAttributeLog > contactJsonAttributeLogs,
            final LeadRequest source, final ObjectType objectType ) {
        addIfNotNullContactJsonAttributeLogs( contactJsonAttributeLogs,
                ContactJsonAttributeType.SAVED_SEARCH_VALUES.getKey(), source.getSavedSearchValues(), objectType );
        addIfNotNullContactJsonAttributeLogs( contactJsonAttributeLogs,
                ContactJsonAttributeType.PROPERTY_TOUR_INFORMATION.getKey(), source.getPropertyTourInformation(),
                objectType );
        addIfNotNullContactJsonAttributeLogs( contactJsonAttributeLogs,
                ContactJsonAttributeType.ADDITIONAL_PROPERTY_DATA.getKey(), source.getAdditionalPropertyData(),
                objectType );
        addIfNotNullContactJsonAttributeLogs( contactJsonAttributeLogs,
                ContactJsonAttributeType.WEBSITE_SESSION_DATA.getKey(), source.getWebsiteSessionData(), objectType );
        addIfNotNullContactJsonAttributeLogs( contactJsonAttributeLogs, ContactJsonAttributeType.ORDER_ID.getKey(),
                source.getOrderId(), objectType );
        addIfNotNullContactJsonAttributeLogs( contactJsonAttributeLogs,
                ProspectAttributeType.FARMING_BUYER_ACTIONS.getKey(), source.getFarmingBuyerAction(), objectType );
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
    private void addIfNotNullContactJsonAttributeLogs( final Set< ContactJsonAttributeLog > attributes,
            final String key, final String value, final ObjectType objectType ) {

        boolean flag = false;
        String currentValue = value;
        if (currentValue != null) {
            currentValue = StringUtils.subStringForLength( currentValue, 1000 );
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( final ContactJsonAttributeLog contactJsonAttributeLog : attributes ) {
                if (contactJsonAttributeLog.getObjectAttributeConfig().equals( config )) {
                    if (contactJsonAttributeLog.getValue() != null) {
                        final Map< String, List< String > > jsonMap = JsonUtil
                                .toType( contactJsonAttributeLog.getValue(), Map.class );
                        final List< String > jsonValueList = jsonMap.get( key );
                        jsonValueList.add( currentValue );
                        contactJsonAttributeLog.setValue( JsonUtil.toJson( jsonMap ) );
                    } else {
                        convertToJsonString( key, currentValue );
                        contactJsonAttributeLog.setValue( currentValue );
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                final ContactJsonAttributeLog contactJsonAttributeLog = new ContactJsonAttributeLog();
                contactJsonAttributeLog.setValue( convertToJsonString( key, currentValue ) );
                contactJsonAttributeLog.setObjectAttributeConfig( config );
                attributes.add( contactJsonAttributeLog );
            }
        }
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

    @Override
    public LeadRequest convertFrom( ContactLog source, LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }
}
