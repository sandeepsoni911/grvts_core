package com.owners.gravitas.enums;

/**
 * The Enum MLSPackageType.
 *
 * @author vishwanathm
 */
public enum MLSPackageType {

    /** The OWNE r_ adviso r_12. */
    OWNER_ADVISOR_12_MONTHS( "Owners Advisor – 12 Months" ),

    /** The OWNE r_ adviso r_6. */
    OWNER_ADVISOR_6_MONTHS( "Owners Advisor – 6 Months" ),

    /** The MI n_ servic e_ stat e_6. */
    MIN_SERVICE_STATE_6_MONTHS( "Minimum Service State – 6 months" ),

    /** The MI n_ servic e_ stat e_12. */
    MIN_SERVICE_STATE_12_MONTHS( "Minimum Service State – 12 months" ),

    /** The free. */
    FREE( "Free" ),

    /** The basic. */
    BASIC( "Basic" ),

    /** The professional. */
    PROFESSIONAL( "Professional" ),

    /** The premium. */
    PREMIUM( "Premium" ),

    /** The basic mls. */
    BASIC_MLS( "Basic MLS" ),

    /** The flat free mls. */
    FLAT_FEE_MLS( "Flat Fee MLS" ),

    /** The premium mls. */
    PREMIUM_MLS( "Premium MLS" ),

    /** The free listing. */
    FREE_LISTING( "Free Listing" );

    /** The package type. */
    private String packageType;

    /**
     * Instantiates a new MLS package type.
     *
     * @param packageType
     *            the package type
     */
    private MLSPackageType( final String packageType ) {
        this.packageType = packageType;
    }

    /**
     * Gets the package type.
     *
     * @return the package type
     */
    public String getPackageType() {
        return packageType;
    }
}
