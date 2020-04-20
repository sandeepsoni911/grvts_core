package com.owners.gravitas.config;

public class LiveVoxMatchingCampaignConfiguration {

    private Integer serviceId;
    private Integer clientId;

    public LiveVoxMatchingCampaignConfiguration() {

    }

    public LiveVoxMatchingCampaignConfiguration( Integer serviceId, Integer clientId ) {
        this.serviceId = serviceId;
        this.clientId = clientId;
    }

    /**
     * @return the serviceId
     */
    public Integer getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId
     *            the serviceId to set
     */
    public void setServiceId( Integer serviceId ) {
        this.serviceId = serviceId;
    }

    /**
     * @return the clientId
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * @param clientId
     *            the clientId to set
     */
    public void setClientId( Integer clientId ) {
        this.clientId = clientId;
    }

}
