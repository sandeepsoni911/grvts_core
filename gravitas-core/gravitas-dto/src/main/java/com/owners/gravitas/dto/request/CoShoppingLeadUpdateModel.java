package com.owners.gravitas.dto.request;

/**
 * The class CoShoppingLeadUpdateModel.
 * 
 * @author imranmoh
 *
 */
public class CoShoppingLeadUpdateModel extends BaseRequest {

    /*** The serialVersionUID */
    private static final long serialVersionUID = 6501059062809604283L;

    /** The co shopping Id. */
    private String id;

    /** The property tour information. */
    private String propertyTourInformation;

    /** The lead request status. */
    private String status;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId( final String id ) {
        this.id = id;
    }

    /**
     * @return the propertyTourInformation
     */
    public String getPropertyTourInformation() {
        return propertyTourInformation;
    }

    /**
     * @param propertyTourInformation
     *            the propertyTourInformation to set
     */
    public void setPropertyTourInformation( final String propertyTourInformation ) {
        this.propertyTourInformation = propertyTourInformation;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CoShoppingLeadUpdateModel [id=" + id + ", propertyTourInformation=" + propertyTourInformation
                + ", status=" + status + "]";
    }
}
