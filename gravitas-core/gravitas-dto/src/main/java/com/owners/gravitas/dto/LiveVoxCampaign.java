package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxCampaign {

    private int id;
    private String name;
    private int typeId;
    private int serviceId;
    private String status;
    private long uploadDate;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId( int id ) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * @param typeId
     *            the typeId to set
     */
    public void setTypeId( int typeId ) {
        this.typeId = typeId;
    }

    /**
     * @return the serviceId
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId
     *            the serviceId to set
     */
    public void setServiceId( int serviceId ) {
        this.serviceId = serviceId;
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
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * @return the uploadDate
     */
    public long getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate
     *            the uploadDate to set
     */
    public void setUploadDate( long uploadDate ) {
        this.uploadDate = uploadDate;
    }

}
