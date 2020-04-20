package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

@JsonIgnoreProperties( ignoreUnknown = true )
public class BuyerDevice extends BaseDTO {

    /** The serial version id **/
    private static final long serialVersionUID = -2905391530880066040L;

    private String deviceId;

    private String deviceType;

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     *            the deviceId to set
     */
    public void setDeviceId( String deviceId ) {
        this.deviceId = deviceId;
    }

    /**
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType
     *            the deviceType to set
     */
    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BuyerDevice [deviceId=" + deviceId + ", deviceType=" + deviceType + "]";
    }
}
