package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

@JsonIgnoreProperties( ignoreUnknown = true )
public class BuyerDeviceResponse extends BaseDTO {

    /*** The serial version id */
    private static final long serialVersionUID = -7990376971302857521L;

    private BuyerDeviceDetail result;

    /**
     * @return the result
     */
    public BuyerDeviceDetail getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult( BuyerDeviceDetail result ) {
        this.result = result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BuyerDeviceResponse [result=" + result + "]";
    }
}
