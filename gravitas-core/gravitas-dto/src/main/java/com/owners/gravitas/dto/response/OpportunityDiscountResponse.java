package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class OpportunityDiscountResponse.
 * 
 * @author kushwaja
 */
@JsonInclude( Include.NON_NULL )
public class OpportunityDiscountResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5880307885754726165L;

    private String oppFbId;
    
    private String oclDiscount;

    private String ptsDiscount;

    private String buySellDiscount;

    private String maxDiscount;
    
    public OpportunityDiscountResponse() {  
    }
    
    public OpportunityDiscountResponse(Status status, String message ) {
        super(status,message);
    }

    /**
     * @return
     */
    public String getOppFbId() {
        return oppFbId;
    }

    /**
     * @param oppFbId
     */
    public void setOppFbId( String oppFbId ) {
        this.oppFbId = oppFbId;
    }

    /**
     * @return
     */
    public String getOclDiscount() {
        return oclDiscount;
    }

    /**
     * @param oclDiscount
     */
    public void setOclDiscount( String oclDiscount ) {
        this.oclDiscount = oclDiscount;
    }

    /**
     * @return
     */
    public String getPtsDiscount() {
        return ptsDiscount;
    }

    /**
     * @param ptsDiscount
     */
    public void setPtsDiscount( String ptsDiscount ) {
        this.ptsDiscount = ptsDiscount;
    }

    /**
     * @return
     */
    public String getBuySellDiscount() {
        return buySellDiscount;
    }

    /**
     * @param buySellDiscount
     */
    public void setBuySellDiscount( String buySellDiscount ) {
        this.buySellDiscount = buySellDiscount;
    }

    /**
     * @return
     */
    public String getMaxDiscount() {
        return maxDiscount;
    }

    /**
     * @param maxDiscount
     */
    public void setMaxDiscount( String maxDiscount ) {
        this.maxDiscount = maxDiscount;
    }

    @Override
    public String toString() {
        return "OpportunityDiscountResponse [oppFbId=" + oppFbId + ", oclDiscount=" + oclDiscount + ", ptsDiscount="
                + ptsDiscount + ", buySellDiscount=" + buySellDiscount + ", maxDiscount=" + maxDiscount + "]";
    }

}
