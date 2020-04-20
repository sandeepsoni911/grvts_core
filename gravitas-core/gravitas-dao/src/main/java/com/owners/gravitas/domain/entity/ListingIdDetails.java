package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class ListingIdDetails.
 */
@Entity( name = "GR_LISTING_OPPORTUNITY" )
public class ListingIdDetails extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The listing id. */
    @Column( name = "LISTING_ID" )
    private String listingId;

    /** The opportunity. */
    @ManyToOne
    @JoinColumn( name = "OPPORTUNITY_ID", insertable = false, updatable = false )
    private Opportunity opportunity;

    /**
     * Gets the listing id.
     *
     * @return the listing id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the new listing id
     */
    public void setListingId( String listingId ) {
        this.listingId = listingId;
    }

    /**
     * Gets the opportunity.
     *
     * @return the opportunity
     */
    public Opportunity getOpportunity() {
        return opportunity;
    }

    /**
     * Sets the opportunity.
     *
     * @param opportunity
     *            the new opportunity
     */
    public void setOpportunity( Opportunity opportunity ) {
        this.opportunity = opportunity;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( listingId == null ) ? 0 : listingId.hashCode() );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListingIdDetails other = ( ListingIdDetails ) obj;
        if (listingId == null) {
            if (other.listingId != null)
                return false;
        } else if (!listingId.equals( other.listingId ))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListingIdDetails [listingId=");
        builder.append(listingId);
        builder.append("]");
        return builder.toString();
    }
}
