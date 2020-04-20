package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class Contact.
 *
 * @author kushwaja
 *         The Class Discount.
 */
@Entity( name = "GR_DISCOUNT" )
public class Discount extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    @Column( name = "STATE" )
    private String state;

    @Column( name = "MINIMUM_SALE_PRICE" )
    private int minumumSalePrice;

    @Column( name = "OCL_DISCOUNT" )
    private String oclDiscount;

    @Column( name = "PTS_DISCOUNT" )
    private String ptsDiscount;

    @Column( name = "BUY_SELL_DISCOUNT" )
    private String buySellDiscount;

    @Column( name = "MAX_DISCOUNT" )
    private String maxDiscount;

    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState( String state ) {
        this.state = state;
    }

    /**
     * @return
     */
    public int getMinumumSalePrice() {
        return minumumSalePrice;
    }

    /**
     * @param minumumSalePrice
     */
    public void setMinumumSalePrice( int minumumSalePrice ) {
        this.minumumSalePrice = minumumSalePrice;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Discount [state=" + state + ", minumumSalePrice=" + minumumSalePrice + ", oclDiscount=" + oclDiscount
                + ", ptsDiscount=" + ptsDiscount + ", buySellDiscount=" + buySellDiscount + ", maxDiscount="
                + maxDiscount + "]";
    }

}
