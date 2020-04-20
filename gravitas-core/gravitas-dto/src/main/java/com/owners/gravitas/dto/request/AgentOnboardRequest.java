/**
 *
 */
package com.owners.gravitas.dto.request;

import static com.owners.gravitas.constants.Constants.REG_EXP_EMAIL;
import static com.owners.gravitas.constants.Constants.REG_EXP_NUMERICS;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.UserAddress;

/**
 * The Class AgentOnboardRequest.
 *
 * @author harshads
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class AgentOnboardRequest extends Agent {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5468826010179809612L;

    /** The photo data. */
    private byte[] photoData;

    /** The role id. */
    private String roleId;

    /** The delete file. */
    private boolean deleteFile;

    /**
     * Gets the photo data.
     *
     * @return the photoData
     */
    public byte[] getPhotoData() {
        return photoData;
    }

    /**
     * Sets the photo data.
     *
     * @param photoData
     *            the photoData to set
     */
    public void setPhotoData( final byte[] photoData ) {
        this.photoData = photoData;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getFirstName()
     */
    @NotBlank( message = "error.agent.firstname.required" )
    @Size( min = 1, max = 60, message = "error.agent.firstname.size" )
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getLastName()
     */
    @NotBlank( message = "error.agent.lastname.required" )
    @Size( min = 1, max = 60, message = "error.agent.lastname.size" )
    @Override
    public String getLastName() {
        return super.getLastName();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getEmail()
     */
    @NotBlank( message = "error.agent.email.required" )
    @Size( max = 150, message = "error.agent.email.size" )
    @Email( message = "error.agent.email.format", regexp = REG_EXP_EMAIL )
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getPhone()
     */
    @NotBlank( message = "error.agent.phone.required" )
    @Size( min = 10, max = 10, message = "error.agent.phone.size" )
    @Pattern( regexp = REG_EXP_NUMERICS, message = "error.agent.phone.format" )
    @Override
    public String getPhone() {
        return super.getPhone();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getBiodata()
     */
    @Size( max = 500, message = "error.agent.biodata.size" )
    @Override
    public String getBiodata() {
        return super.getBiodata();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getAddress()
     */
    @Valid
    @NotNull( message = "error.agent.address.required" )
    @Override
    public UserAddress getAddress() {
        return super.getAddress();
    }

    /**
     * Gets the role id.
     *
     * @return the roleId
     */
    @NotBlank( message = "error.agent.role.required" )
    public String getRoleId() {
        return roleId;
    }

    /**
     * Sets the role id.
     *
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId( String roleId ) {
        this.roleId = roleId;
    }

    /**
     * Gets the personal email.
     *
     * @return the personalEmail
     */
    @NotBlank( message = "error.agent.personal.email.required" )
    @Email( message = "error.agent.personal.email.format", regexp = REG_EXP_EMAIL )
    @Size( max = 150, message = "error.agent.personal.email.size" )
    @Override
    public String getPersonalEmail() {
        return super.getPersonalEmail();
    }

    /**
     * Gets the agent starting date.
     *
     * @return the agentStartingDate
     */
    @NotBlank( message = "error.agent.starting.date.required" )
    @Override
    public String getAgentStartingDate() {
        return super.getAgentStartingDate();
    }

    /**
     * Gets the driving radius.
     *
     * @return the drivingRadius
     */
    @NotBlank( message = "error.agent.driving.radius.required" )
    @Override
    public String getDrivingRadius() {
        return super.getDrivingRadius();
    }

    /**
     * Checks if is delete file.
     *
     * @return true, if is delete file
     */
    public boolean isDeleteFile() {
        return deleteFile;
    }

    /**
     * Sets the delete file.
     *
     * @param deleteFile
     *            the new delete file
     */
    public void setDeleteFile( boolean deleteFile ) {
        this.deleteFile = deleteFile;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getLicense()
     */
    @NotBlank( message = "error.agent.license.required" )
    @Size( min = 1, max = 25, message = "error.agent.license.size" )
    @Override
    public String getLicense() {
        return super.getLicense();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.Agent#getLanguage()
     */
    @NotBlank( message = "error.agent.language.required" )
    @Override
    public String getLanguage() {
        return super.getLanguage();
    }

    /**
     * @see com.owners.gravitas.dto.Agent#getManagingBrokerId()
     */
    @NotBlank( message = "error.agent.managing.broker.required" )
    @Override
    public String getManagingBrokerId() {
        return super.getManagingBrokerId();
    }
}
