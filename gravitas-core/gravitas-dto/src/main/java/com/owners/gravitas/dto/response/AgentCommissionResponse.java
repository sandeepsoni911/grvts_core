package com.owners.gravitas.dto.response;

/**
 * The Class AgentCommissionResponse.
 *
 * @author bhardrah
 */
public class AgentCommissionResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8173259203567317433L;

    /** The commission. */
    private String commission;

    /**
     * Gets the commission.
     *
     * @return the commission
     */
    public String getCommission() {
        return commission;
    }

    /**
     * Sets the commission.
     *
     * @param commission
     *            the commission to set
     */
    public void setCommission( final String commission ) {
        this.commission = commission;
    }

}
