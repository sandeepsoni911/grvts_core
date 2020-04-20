package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class OwnersMarketCbsaLabel.
 * 
 * @author ankusht
 */
@Entity( name = "GR_OWNERS_MARKET_CBSA_LABEL" )
public class OwnersMarketCbsaLabel extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2413772532761281562L;

    /** The owners market label. */
    @Column( name = "OWNERS_MARKET_LABEL", nullable = false )
    private String ownersMarketLabel;

    /** The cbsa label. */
    @Column( name = "CBSA_LABEL", nullable = false )
    private String cbsaLabel;

    /**
     * Gets the owners market label.
     *
     * @return the owners market label
     */
    public String getOwnersMarketLabel() {
        return ownersMarketLabel;
    }

    /**
     * Sets the owners market label.
     *
     * @param ownersMarketLabel
     *            the new owners market label
     */
    public void setOwnersMarketLabel( final String ownersMarketLabel ) {
        this.ownersMarketLabel = ownersMarketLabel;
    }

    /**
     * Gets the cbsa label.
     *
     * @return the cbsa label
     */
    public String getCbsaLabel() {
        return cbsaLabel;
    }

    /**
     * Sets the cbsa label.
     *
     * @param cbsaLabel
     *            the new cbsa label
     */
    public void setCbsaLabel( final String cbsaLabel ) {
        this.cbsaLabel = cbsaLabel;
    }

}
