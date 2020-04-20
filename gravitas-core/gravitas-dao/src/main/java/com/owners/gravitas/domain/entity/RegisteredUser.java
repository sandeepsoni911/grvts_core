package com.owners.gravitas.domain.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The class RegisteredUser
 * 
 * @author bhardrah
 */
@Entity( name = "gr_registered_user" )
public class RegisteredUser implements Serializable {

    private static final long serialVersionUID = 1917640426000266177L;

    /** The user Id. */
    @Id
    @Column( name = "user_Id")
    private String userId;

    /** The email. */
    @Column( name = "email", nullable = false )
    private String email;

    /** The deviceId. */
    @Column( name = "device_id", nullable = true )
    private String deviceId;

    /** The createdOn. */
    @Column( name = "created_on", nullable = false )
    private Timestamp createdOn;

    /** The modifiedOn. */
    @Column( name = "modified_on", nullable = false )
    private Timestamp modifiedOn;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId( String userId ) {
        this.userId = userId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail( String email ) {
        this.email = email;
    }

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
     * @return the createdOn
     */
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn( Timestamp createdOn ) {
        this.createdOn = createdOn;
    }

    /**
     * @return the modifiedOn
     */
    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    /**
     * @param modifiedOn
     *            the modifiedOn to set
     */
    public void setModifiedOn( Timestamp modifiedOn ) {
        this.modifiedOn = modifiedOn;
    }
}
