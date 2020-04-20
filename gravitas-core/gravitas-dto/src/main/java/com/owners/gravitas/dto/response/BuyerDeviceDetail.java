package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

@JsonIgnoreProperties( ignoreUnknown = true )
public class BuyerDeviceDetail extends BaseDTO {

    /** The serial version id **/
    private static final long serialVersionUID = -2905391530880066040L;

    private String status;

    private String message;

    private String deviceIds;

    private List<BuyerDevice> devices;

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
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage( String message ) {
        this.message = message;
    }

    /**
     * @return the deviceIds
     */
    public String getDeviceIds() {
        return deviceIds;
    }

    /**
     * @param deviceIds
     *            the deviceIds to set
     */
    public void setDeviceIds( String deviceIds ) {
        this.deviceIds = deviceIds;
    }

    /**
     * @return the devices
     */
    public List< BuyerDevice > getDevices() {
        return devices;
    }

    /**
     * @param devices
     *            the devices to set
     */
    public void setDevices( List< BuyerDevice > devices ) {
        this.devices = devices;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BuyerDeviceDetail [status=" + status + ", message=" + message + ", deviceIds=" + deviceIds
                + ", devices=" + devices + "]";
    }
}
