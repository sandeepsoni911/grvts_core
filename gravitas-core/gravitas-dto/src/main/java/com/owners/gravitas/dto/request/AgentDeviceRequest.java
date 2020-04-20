package com.owners.gravitas.dto.request;

import org.hibernate.validator.constraints.NotBlank;

import com.hubzu.notification.dto.client.push.AppType;

/**
 * The Class AgentDeviceRequest.
 *
 * @author amits
 */
public class AgentDeviceRequest {

    /** The device id. */
    @NotBlank( message = "error.agent.device.id.required" )
    private String deviceId;

    /** The device type. */
    private AppType deviceType;

    /**
     * Gets the device id.
     *
     * @return the device id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the device id.
     *
     * @param deviceId
     *            the new device id
     */
    public void setDeviceId( final String deviceId ) {
        this.deviceId = deviceId;
    }

   
    /**
     * Gets the device type.
     *
     * @return the device type
     */
    public AppType getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the device type.
     *
     * @param deviceType
     *            the deviceType to set
     */
    public void setDeviceType( final AppType deviceType ) {
        this.deviceType = deviceType;
    }
    
}
