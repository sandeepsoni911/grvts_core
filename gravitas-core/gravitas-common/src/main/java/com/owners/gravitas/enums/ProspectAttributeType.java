package com.owners.gravitas.enums;

/**
 * The Enum ProspectAttributeType.
 *
 * @author pabhishek
 */
public enum ProspectAttributeType {

    /** The interested zip codes. */
    INTERESTED_ZIP_CODES( "interestedZipCodes", "string" ),

    /** The marketing opt in. */
    MARKETING_OPT_IN( "marketingOptIn", "string" ),

    /** The lead source url. */
    LEAD_SOURCE_URL( "leadSourceUrl", "string" ),

    /** The website. */
    WEBSITE( "website", "string" ),

    /** The buyer lead quality. */
    BUYER_LEAD_QUALITY( "buyerLeadQuality", "string" ),

    /** The preferred contact method. */
    PREFERRED_CONTACT_METHOD( "preferredContactmethod", "string" ),

    /** The prefrred contact time. */
    PREFRRED_CONTACT_TIME( "preferredContactTime", "date" ),

    /** The preferred language. */
    PREFERRED_LANGUAGE( "preferredLanguage", "string" ),

    /** The owners visitor id. */
    OWNERS_VISITOR_ID( "ownersVisitorId", "string" ),

    /** The last visit date time. */
    LAST_VISIT_DATE_TIME( "lastVisitDateTime", "date" ),

    /** The source. */
    SOURCE( "source", "string" ),

    /** The working with realtor. */
    WORKING_WITH_REALTOR( "workingWithRealtor", "string" ),

    /** The BUYE R READINES S TIM el INE. */
    BUYER_READINESS_TIMElINE( "buyerReadinessTimeline", "string" ),

    /** The unbounce page variant. */
    UNBOUNCE_PAGE_VARIANT( "unbouncePageVariant", "string" ),

    /** The gcl id. */
    GCL_ID( "gclId", "string" ),

    /** The comments. */
    COMMENTS( "comments", "string" ),

    /** The own home. */
    OWN_HOME( "ownHome", "boolean" ),

    /** The interested in financing. */
    INTERESTED_IN_FINANCING( "interestedInFinancing", "boolean" ),

    /** The loan number. */
    LOAN_NUMBER( "loanNumber", "string" ),

    /** The financing. */
    FINANCING( "financing", "string" ),

    /** The price range. */
    PRICE_RANGE( "priceRange", "string" ),

    /** The median price. */
    MEDIAN_PRICE( "medianPrice", "double" ),

    /** The additional property data. */
    ADDITIONAL_PROPERTY_DATA( "additionalPropertyData", "string" ),

    /** The saved search values. */
    SAVED_SEARCH_VALUES( "savedSearchValues", "string" ),

    /** The website session data. */
    WEBSITE_SESSION_DATA( "websiteSessionData", "string" ),

    /** The request type. */
    REQUEST_TYPE( "requestType", "string" ),

    /** The pre approved for mortgage. */
    PRE_APPROVED_FOR_MORTGAGE( "preApprovedForMortgage", "string" ),

    /** The offer amount. */
    OFFER_AMOUNT( "offerAmount", "double" ),

    /** The earnest money deposit. */
    EARNEST_MONEY_DEPOSIT( "earnestMoneyDeposit", "double" ),

    /** The purchase method. */
    PURCHASE_METHOD( "purchaseMethod", "string" ),

    /** The down payment. */
    DOWN_PAYMENT( "downPayment", "double" ),

    /** The property tour information. */
    PROPERTY_TOUR_INFORMATION( "propertyTourInformation", "string" ),

    /** The message. */
    MESSAGE( "message", "string" ),

    /** The mls id. */
    MLS_ID( "mlsId", "string" ),

    /** The owners com identifier. */
    OWNERS_COM_IDENTIFIER( "ownersComIdentifier", "string" ),

    /** The state. */
    STATE( "state", "string" ),

    /** The stage. */
    STAGE( "stage", "string" ),

    /** The property address. */
    PROPERTY_ADDRESS( "propertyAddress", "string" ),

    /** The al id. */
    AL_ID( "alId", "string" ),

    /** The mls package type. */
    MLS_PACKAGE_TYPE( "mlsPackageType", "string" ),

    /** The listing creation date. */
    LISTING_CREATION_DATE( "listingCreationDate", "date" ),

    /** The buyer lead score. */
    BUYER_LEAD_SCORE( "buyerLeadScore", "string" ),

    /** The buyer lead label. */
    BUYER_LEAD_LABEL( "buyerLeadLabel", "string" ),

    /** The farming status. */
    FARMING_SYSTEM_ACTIONS( "farmingSystemActions", "string" ),

    /** The failure reason code. */
    FARMING_FAILURE_CODE( "farmingfailureCode", "string" ),

    /** The notes. */
    NOTES( "notes", "string" ),

    /** The farming group. */
    FARMING_GROUP( "farmingGroup", "string" ),

    /** The farming status. */
    FARMING_STATUS( "farmingStatus", "string" ),

    /** The farming buyer actions. */
    FARMING_BUYER_ACTIONS( "farmingBuyerActions", "string" ),

    /** The actual closing date. */
    ACTUAL_CLOSING_DATE( "actualClosingDate", "string" ),

    /** The Constant EXPECTED_AGENT_REVENUE. */
    EXPECTED_AGENT_REVENUE( "expectedAgentRevenue", "string" ),

    /** The Constant EXPECTED_OWNERS_COM_REVENUE. */
    EXPECTED_OWNERS_COM_REVENUE( "expectedOwnersComRevenue", "string" ),

    /** The Constant SALES_PRICE. */
    SALES_PRICE( "salesPrice", "string" ),

    /** The Constant LISTING_ID. */
    LISTING_ID( "listingId", "string" ),

    /** The Constant LEAD_SOURCE. */
    LEAD_SOURCE( "leadSource", "string" ),

    /** The property city. */
    PROPERTY_CITY( "propertyCity", "string" ),

    /** The property type. */
    PROPERTY_TYPE( "propertyType", "string" ),

    /** The property bedroom. */
    PROPERTY_BEDROOM( "propertyBedroom", "double" ),

    /** The property bathroom. */
    PROPERTY_BATHROOM( "propertyBathroom", "double" ),

    /** The property square foot. */
    PROPERTY_SQUAREFOOT( "propertySquareFoot", "double" ),

    /** The property city. */
    INQUIRY_DATE( "inquiryDate", "date" ),

    /** The search city. */
    SEARCH_CITY( "searchCity", "string" ),

    /** The search attributes. */
    SEARCH_ATTRIBUTES( "searchAttributes", "string" ),

    /** The partner identifier. */
    PARTNER_IDENTIFIER( "partnerIdentifier", "string" ),

    /** The interested in financing. */
    SUBMITTED_TO_LIVEVOX( "submittedToLiveVox", "boolean" ),

    /** The Lot size. */
    LOT_SIZE( "lotSize", "string" ),

    /** The Credit score. */
    CREDIT_SCORE( "creditScore", "string" ),

    /** The home type. */
    HOME_TYPE( "homeType", "string" ),

    /** The browser. */
    BROWSER( "browser", "string" ),

    /** The Operating system. */
    OS( "os", "string" ),

    /** The device. */
    DEVICE( "device", "string" ),

    /** The best time to contact. */
    BEST_TIME_TO_CONTACT( "bestTimeToContact", "string" ),

    /** The is client expecting call. */
    IS_CLIENT_EXPECTING_CALL( "isClientExpectingCall", "string" ),

    /** The first time home buyer. */
    FIRST_TIME_HOME_BUYER( "firstTimeHomeBuyer", "string" ),

    /** The selling home as part of purchase. */
    SELLING_HOME_AS_PART_OF_PURCHASE( "sellingHomeAsPartOfPurchase", "string" );

    /** The key. */
    private String key;

    /** The data type. */
    private String dataType;

    /**
     * Instantiates a new prospect attribute type.
     *
     * @param key
     *            the key
     * @param dataType
     *            the data type
     */
    private ProspectAttributeType( final String key, final String dataType ) {
        this.key = key;
        this.dataType = dataType;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the data type.
     *
     * @return the data type
     */
    public String getDataType() {
        return dataType;
    }

}
