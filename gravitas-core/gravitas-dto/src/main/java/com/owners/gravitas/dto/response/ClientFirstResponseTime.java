package com.owners.gravitas.dto.response;

/**
 * The Class ClientFirstResponseTime.
 *
 * @author raviz
 */
public class ClientFirstResponseTime extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7965023709001077440L;

    /** The response time. */
    private String allResponseTime;

    /** The buyer response time. */
    private String buyerResponseTime;

    /** The seller response time. */
    private String sellerResponseTime;

    /**
     * Gets the all response time.
     *
     * @return the all response time
     */
    public String getAllResponseTime() {
        return allResponseTime;
    }

    /**
     * Sets the all response time.
     *
     * @param allResponseTime
     *            the new all response time
     */
    public void setAllResponseTime( final String allResponseTime ) {
        this.allResponseTime = allResponseTime;
    }

    /**
     * Gets the buyer response time.
     *
     * @return the buyer response time
     */
    public String getBuyerResponseTime() {
        return buyerResponseTime;
    }

    /**
     * Sets the buyer response time.
     *
     * @param buyerResponseTime
     *            the new buyer response time
     */
    public void setBuyerResponseTime( final String buyerResponseTime ) {
        this.buyerResponseTime = buyerResponseTime;
    }

    /**
     * Gets the seller response time.
     *
     * @return the seller response time
     */
    public String getSellerResponseTime() {
        return sellerResponseTime;
    }

    /**
     * Sets the seller response time.
     *
     * @param sellerResponseTime
     *            the new seller response time
     */
    public void setSellerResponseTime( final String sellerResponseTime ) {
        this.sellerResponseTime = sellerResponseTime;
    }

}
