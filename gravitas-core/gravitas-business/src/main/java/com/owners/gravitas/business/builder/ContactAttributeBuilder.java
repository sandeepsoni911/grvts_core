package com.owners.gravitas.business.builder;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.ProspectAttributeType;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;

/**
 * The Class ContactAttributeBuilder.
 *
 * @author shivamm
 */
@Component( "contactAttributeBuilder" )
public class ContactAttributeBuilder extends AbstractBuilder< Set< ContactAttribute >, LeadRequest > {

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    /**
     * Convert from.
     *
     * @param source
     *            the source
     * @param crmLeadResponse
     *            the crm lead response
     * @return the sets the
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    public Set< ContactAttribute > convertFrom( final LeadRequest source, Set< ContactAttribute > destination ) {
        Set< ContactAttribute > contactAttribute = destination;
        if (source != null) {
            if (contactAttribute == null) {
                contactAttribute = new HashSet< ContactAttribute >();
            }
            final ObjectType objectType = objectTypeService.findByName( "lead" );
            setContactAttributes( source, contactAttribute, objectType );
        }
        return contactAttribute;
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
    private void setContactAttributes( final LeadRequest source, Set< ContactAttribute > contactAttribute,
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
            if (currentValue.length() > 1000) {
                currentValue = currentValue.substring( 0, 1000 );
            }
            final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key,
                    objectType );
            for ( ContactAttribute contactAttribute : attributes ) {
                if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                    contactAttribute.setValue( currentValue );
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                final ContactAttribute contactAttribute = new ContactAttribute();
                contactAttribute.setValue( currentValue );
                contactAttribute.setObjectAttributeConfig(config);
                attributes.add( contactAttribute );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public LeadRequest convertTo( Set< ContactAttribute > source, LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertTo is not supported" );
    }
}
