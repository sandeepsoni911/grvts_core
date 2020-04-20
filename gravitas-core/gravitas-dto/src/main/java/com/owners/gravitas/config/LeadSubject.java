package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The LeadEmailAddress for email scraping subjects.
 *
 * @author vishwanathm
 */
@Component
public class LeadSubject {

    /** The subject filters. */
    @Value( "${hubzu.affiliate.mail.subject.hubzu}" )
    private String hubzuSubject;

    /** The subject filters. */
    @Value( "${hubzu.affiliate.mail.subject.homes}" )
    private String homesSubject;

    /** The subject filters. */
    @Value( "${hubzu.affiliate.mail.subject.trulia}" )
    private String truliaSubject;

    /** The subject filters. */
    @Value( "${hubzu.affiliate.mail.subject.Homebidz}" )
    private String homeBidzSubject;

    /** The subject filters. */
    @Value( "${hubzu.affiliate.mail.subject.zillow}" )
    private String zillowSubject;

    /** The subject filters. */
    @Value( "${hubzu.affiliate.mail.subject.realtor}" )
    private String realtorSubject;

    /** The subject filters. */
    @Value( "${hubzu.affiliate.mail.subject.ultraForce}" )
    private String ultraforceSubject;

    /** The subject filters. */
    @Value( "${owners.affiliate.mail.subject.owners}" )
    private String ownersSubject;

    /** The subject filters. */
    @Value( "${owners.affiliate.mail.subject.realtor}" )
    private String ownersRealtorSubject;

    /** The subject filters */
    @Value( "${owners.affiliate.sellerLeads.mail.subject}" )
    private String ownersSellerLeadSubject;

    /** The owners seller lead subject from request results. */
    @Value( "${owners.affiliate.sellerLeads.mail.requestResults.subject}" )
    private String ownersSellerLeadRequestResultSubject;

    /** The owners seller lead subject from homes. */
    @Value( "${owners.affiliate.sellerLeads.mail.homes.subject}" )
    private String ownersSellerLeadHomesSubject;

    /**
     * Gets the hubzu subject.
     *
     * @return the hubzu subject
     */
    public String getHubzuSubject() {
        return toLowerCase( hubzuSubject );
    }

    /**
     * Gets the homes subject.
     *
     * @return the homes subject
     */
    public String getHomesSubject() {
        return toLowerCase( homesSubject );
    }

    /**
     * Gets the trulia subject.
     *
     * @return the trulia subject
     */
    public String getTruliaSubject() {
        return toLowerCase( truliaSubject );
    }

    /**
     * Gets the home bidz subject.
     *
     * @return the home bidz subject
     */
    public String getHomeBidzSubject() {
        return toLowerCase( homeBidzSubject );
    }

    /**
     * Gets the zillow subject.
     *
     * @return the zillow subject
     */
    public String getZillowSubject() {
        return toLowerCase( zillowSubject );
    }

    /**
     * Gets the realtor subject.
     *
     * @return the realtor subject
     */
    public String getRealtorSubject() {
        return toLowerCase( realtorSubject );
    }

    /**
     * Gets the ultraforce subject.
     *
     * @return the ultraforce subject
     */
    public String getUltraforceSubject() {
        return toLowerCase( ultraforceSubject );
    }

    /**
     * To lower case.
     *
     * @param input
     *            the input
     * @return the string
     */
    private String toLowerCase( final String input ) {
        return input.toLowerCase();
    }

    /**
     * Gets the owners subject.
     *
     * @return the owners subject
     */
    public String getOwnersSubject() {
        return ownersSubject.toLowerCase();
    }

    /**
     * Gets the owners realtor subject.
     *
     * @return the owners realtor subject
     */
    public String getOwnersRealtorSubject() {
        return ownersRealtorSubject.toLowerCase();
    }

    /**
     * Gets owners seller lead subject
     *
     * @return the owners seller lead subject
     */
    public String getOwnersSellerLeadSubject() {
        return ownersSellerLeadSubject;
    }

    /**
     * Gets owners seller lead subject
     *
     * @return the owners seller lead subject
     */
    public String getOwnersSellerLeadRequestResultSubject() {
        return ownersSellerLeadRequestResultSubject;
    }

    /**
     * Gets owners seller lead subject
     *
     * @return the owners seller lead subject
     */
    public String getOwnersSellerLeadHomesSubject() {
        return ownersSellerLeadHomesSubject;
    }
}
