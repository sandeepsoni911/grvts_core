package com.owners.gravitas.dto.request;

import static com.owners.gravitas.constants.Constants.REGEX_CURRENCY;
import static com.owners.gravitas.constants.Constants.REGEX_STATE;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.UserTimeZone;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.validators.Date;
import com.owners.gravitas.validators.StringEnum;

/**
 * The Class GenericLeadRequest.
 *
 * @author vishwanathm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class GenericLeadRequest extends LeadRequest {
    
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

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getRequestType(java.lang
     * .String)
     */
    @NotBlank( message = "error.lead.requestType.required" )
    @StringEnum( enumClass = LeadRequestType.class, message = "error.lead.requestType.format" )
    public String getRequestType() {
        return super.getRequestType();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequest#getLeadType()
     */
    @NotBlank( message = "error.lead.leadType.required" )
    @StringEnum( enumClass = RecordType.class, message = "error.lead.leadType.format" )
    public String getLeadType() {
        return super.getLeadType();
    }
    
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getLeadStatus(java.lang.
     * String)
     */
    @Size( min = 0, max = 20, message = "error.lead.leadStatus.size" )
    public String getLeadStatus() {
        return super.getLeadStatus();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#
     * getPreApprovedForMortgage(java.lang.String)
     */
    @Size( min = 0, max = 50, message = "error.lead.preApprovedForMortgage.size" )
    public String getPreApprovedForMortgage() {
        return super.getPreApprovedForMortgage();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getWorkingWithRealtor(
     * java.lang.String)
     */
    @Size( min = 0, max = 50, message = "error.lead.workingWithRealtor.size" )
    public String getWorkingWithRealtor() {
        return super.getWorkingWithRealtor();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#
     * getBuyerReadinessTimeline(java.lang.String)
     */
    @Size( min = 0, max = 50, message = "error.lead.buyerReadinessTimeline.size" )
    public String getBuyerReadinessTimeline() {
        return super.getBuyerReadinessTimeline();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#getMarketingOptIn(
     * boolean)
     */
    public boolean isMarketingOptIn() {
        return super.isMarketingOptIn();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getPreferredContactTime(
     * java.lang.String)
     */
    @Size( min = 0, max = 200, message = "error.lead.preferredContactTime.size" )
    public String getPreferredContactTime() {
        return super.getPreferredContactTime();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#
     * getPreferredContactMethod(java.lang.String)
     */
    @Size( min = 0, max = 10, message = "error.lead.preferredContactMethod.size" )
    public String getPreferredContactMethod() {
        return super.getPreferredContactMethod();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getPriceRange(java.lang.
     * String)
     */
    @Size( min = 0, max = 200, message = "error.lead.priceRange.size" )
    public String getPriceRange() {
        return super.getPriceRange();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getBuyerLeadQuality(java
     * .lang.String)
     */
    @Size( min = 0, max = 4, message = "error.lead.buyerLeadQuality.size" )
    public String getBuyerLeadQuality() {
        return super.getBuyerLeadQuality();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getPreferredLanguage(
     * java.lang.String)
     */
    @Size( min = 0, max = 7, message = "error.lead.preferredLanguage.size" )
    public String getPreferredLanguage() {
        return super.getPreferredLanguage();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#getState(java.lang.
     * String)
     */
    @Pattern( regexp = "\\s*|[a-zA-Z]{2}", message = "error.lead.state.format" )
    public String getState() {
        return super.getState();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getPropertyAddress(java.
     * lang.String)
     */
    @Size( min = 0, max = 255, message = "error.lead.propertyAddress.size" )
    public String getPropertyAddress() {
        return super.getPropertyAddress();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#getMlsId(java.lang.
     * String)
     */
    @Size( min = 0, max = 30, message = "error.lead.mlsId.size" )
    public String getMlsId() {
        return super.getMlsId();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#getAlId(java.lang.
     * String)
     */
    @Size( min = 0, max = 30, message = "error.lead.alId.size" )
    public String getAlId() {
        return super.getAlId();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getInterestedZipcodes(
     * java.lang.String)
     */
    @Size( min = 0, max = 200, message = "error.lead.interestedZipcodes.size" )
    public String getInterestedZipcodes() {
        return super.getInterestedZipcodes();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getOwnersComIdentifier(
     * java.lang.String)
     */
    @Size( min = 0, max = 100, message = "error.lead.ownersComIdentifier.size" )
    public String getOwnersComIdentifier() {
        return super.getOwnersComIdentifier();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getOfferAmount(java.lang
     * .String)
     */
    @Size( min = 0, max = 19, message = "error.lead.offerAmount.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.offerAmount.format" )
    public String getOfferAmount() {
        return super.getOfferAmount();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getListingCreationDate(
     * org.joda.time.DateTime)
     */
    @Date( format = DateUtil.DEFAULT_CRM_DATE_PATTERN, message = "error.lead.listingCreationDate.format" )
    public String getListingCreationDate() {
        return super.getListingCreationDate();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getFinancing(java.lang.
     * String)
     */
    @Size( min = 0, max = 30, message = "error.lead.financing.size" )
    public String getFinancing() {
        return super.getFinancing();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getWebsite(java.lang.
     * String)
     */
    @URL( message = "error.lead.website.format" )
    @Size( min = 0, max = 255, message = "error.lead.website.size" )
    public String getWebsite() {
        return super.getWebsite();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getLeadSourceUrl(java.
     * lang.String)
     */
    @NotBlank( message = "error.lead.leadSourceUrl.required" )
    @URL( message = "error.lead.leadSourceUrl.format" )
    @Size( min = 3, max = 255, message = "error.lead.leadSourceUrl.size" )
    public String getLeadSourceUrl() {
        return super.getLeadSourceUrl();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getSavedSearchValues(
     * java.lang.String)
     */
    @Size( min = 0, max = 2048, message = "error.lead.savedSearchValues.size" )
    public String getSavedSearchValues() {
        return super.getSavedSearchValues();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getEarnestMoneyDeposit(
     * java.lang.String)
     */
    @Size( min = 0, max = 19, message = "error.lead.earnestMoneyDeposit.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.earnestMoneyDeposit.format" )
    public String getEarnestMoneyDeposit() {
        return super.getEarnestMoneyDeposit();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getPurchaseMethod(java.
     * lang.String)
     */
    @Size( min = 0, max = 8, message = "error.lead.purchaseMethod.size" )
    public String getPurchaseMethod() {
        return super.getPurchaseMethod();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getDownPayment(java.lang
     * .String)
     */
    @Size( min = 0, max = 19, message = "error.lead.downPayment.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.downPayment.format" )
    public String getDownPayment() {
        return super.getDownPayment();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#
     * getPropertyTourInformation(java.lang.String)
     */
    @Size( min = 0, max = 2048, message = "error.lead.propertyTourInformation.size" )
    public String getPropertyTourInformation() {
        return super.getPropertyTourInformation();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.request.LeadRequestBasic#
     * getAdditionalPropertyData(java.lang.String)
     */
    @Size( min = 0, max = 2048, message = "error.lead.additionalPropertyData.size" )
    public String getAdditionalPropertyData() {
        return super.getAdditionalPropertyData();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getWebsiteSessionData(
     * java.lang.String)
     */
    @Size( min = 0, max = 2048, message = "error.lead.websiteSessionData.size" )
    public String getWebsiteSessionData() {
        return super.getWebsiteSessionData();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getOwnersVisitorId(java.
     * lang.String)
     */
    @Size( min = 0, max = 50, message = "error.lead.ownersVisitorId.size" )
    public String getOwnersVisitorId() {
        return super.getOwnersVisitorId();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getMessage(java.lang.
     * String)
     */
    @Size( min = 0, max = 2048, message = "error.lead.message.size" )
    public String getMessage() {
        return super.getMessage();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getMedianPrice(java.lang
     * .String)
     */
    @Size( min = 0, max = 19, message = "error.lead.medianPrice.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.medianPrice.format" )
    public String getMedianPrice() {
        return super.getMedianPrice();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getLastVisitDateTime(org
     * .joda.time.DateTime)
     */
    @Date( format = DateUtil.DATE_TIME_PATTERN, message = "error.lead.lastVisitDateTime.format" )
    public String getLastVisitDateTime() {
        return super.getLastVisitDateTime();
    }

    // OWNCORE-923
    /**
     * Gets the mls package type.
     *
     * @return the mls package type
     */
    // @StringEnum( enumClass = MLSPackageType.class, message =
    // "error.lead.mlsPackageType.format" )
    public String getMlsPackageType() {
        return super.getMlsPackageType();
    }

    /**
     * Gets the ga campaign.
     *
     * @return the ga campaign
     */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsCampaign.size" )
    public String getGoogleAnalyticsCampaign() {
        return super.getGoogleAnalyticsCampaign();
    }

    /**
     * Gets the ga content.
     *
     * @return the ga content
     */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsContent.size" )
    public String getGoogleAnalyticsContent() {
        return super.getGoogleAnalyticsContent();
    }

    /**
     * Gets the ga medium.
     *
     * @return the ga medium
     */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsMedium.size" )
    public String getGoogleAnalyticsMedium() {
        return super.getGoogleAnalyticsMedium();
    }

    /**
     * Gets the ga source.
     *
     * @return the ga source
     */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsSource.size" )
    public String getGoogleAnalyticsSource() {
        return super.getGoogleAnalyticsSource();
    }

    /**
     * Gets the ga term.
     *
     * @return the ga term
     */
    @Size( min = 0, max = 255, message = "error.lead.googleAnalyticsTerm.size" )
    public String getGoogleAnalyticsTerm() {
        return super.getGoogleAnalyticsTerm();
    }

    /**
     * Gets the unbounce page variant.
     *
     * @return the unbounce page variant
     */
    @Size( min = 0, max = 8, message = "error.lead.unbouncePageVariant.size" )
    public String getUnbouncePageVariant() {
        return super.getUnbouncePageVariant();
    }

    /**
     * Gets the gc lid.
     *
     * @return the gc lid
     */
    @Size( min = 0, max = 250, message = "error.lead.gclId.size" )
    public String getGclId() {
        return super.getGclId();
    }

    /**
     * Checks if is owns home.
     *
     * @return the ownsHome
     */
    public boolean isOwnsHome() {
        return super.isOwnsHome();
    }

    /**
     * Gets the user time zone.
     *
     * @return the user time zone
     */
    public UserTimeZone getUserTimeZone() {
        return super.getUserTimeZone();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.request.LeadRequestBasic#getPropertyState(java.
     * lang. * String)
     */
    @Pattern( regexp = REGEX_STATE, message = "error.lead.state.format" )
    public String getPropertyState() {
        return super.getPropertyState();
    }

    /**
     * Gets the order id.
     *
     * @return the order id
     */
    @Size( min = 0, max = 2048, message = "error.lead.orderId.size" )
    public String getOrderId() {
        return super.getOrderId();
    }

    /**
     * Gets the owners agent.
     *
     * @return the owners agent
     */
    @Size( min = 0, max = 100, message = "error.lead.ownerAgent.size" )
    public String getOwnersAgent() {
        return super.getOwnersAgent();
    }
}
