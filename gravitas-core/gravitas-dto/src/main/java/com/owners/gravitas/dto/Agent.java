/**
 *
 */
package com.owners.gravitas.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Agent.
 *
 * @author harshads
 */
public class Agent extends User {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5670501542452228801L;

    /** The biodata. */
    private String biodata;

    /** The photo url. */
    private String photoUrl;
    
    /** The encodedPhotoData */
    private String encodedPhotoData;

    /** The coverage area. */
    private List< String > coverageArea = new ArrayList< >();

    /** The managing broker id. */
    private String managingBrokerId;

    /** The mobile carrier. */
    private String mobileCarrier;

    /** The agent starting date. */
    private String agentStartingDate;

    /** The notes. */
    private String notes;

    /** The driving radius. */
    private String drivingRadius;

    /** The score. */
    private Double score;

    /** The personal email. */
    private String personalEmail;

    /** The type. */
    private String type;

    /** The license. */
    private String license;

    /** The language. */
    private String language;

    /** The is available. */
    private boolean isAvailable;

    /** The revenue. */
    private Double revenue;

    /**
     * Gets the biodata.
     *
     * @return the biodata
     */
    public String getBiodata() {
        return biodata;
    }

    /**
     * Sets the biodata.
     *
     * @param biodata
     *            the biodata to set
     */
    public void setBiodata( final String biodata ) {
        this.biodata = biodata;
    }

    /**
     * Gets the photo url.
     *
     * @return the photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Sets the photo url.
     *
     * @param photoUrl
     *            the photoUrl to set
     */
    public void setPhotoUrl( final String photoUrl ) {
        this.photoUrl = photoUrl;
    }

    /**
     * Gets the coverage area.
     *
     * @return the coverageArea
     */
    public List< String > getCoverageArea() {
        return coverageArea;
    }

    /**
     * Sets the coverage area.
     *
     * @param coverageArea
     *            the coverageArea to set
     */
    public void setCoverageArea( final List< String > coverageArea ) {
        this.coverageArea = coverageArea;
    }

    /**
     * Adds the coverage area.
     *
     * @param zip
     *            the zip
     * @return true, if successful
     */
    public boolean addCoverageArea( final String zip ) {
        return this.coverageArea.add( zip );
    }

    /**
     * Removes the coverage area.
     *
     * @param zip
     *            the zip
     * @return true, if successful
     */
    public boolean removeCoverageArea( final String zip ) {
        return this.coverageArea.remove( zip );
    }

    /**
     * Gets the managing broker id.
     *
     * @return the managing broker id
     */
    public String getManagingBrokerId() {
        return managingBrokerId;
    }

    /**
     * Sets the managing broker id.
     *
     * @param managingBrokerId
     *            the new managing broker id
     */
    public void setManagingBrokerId( final String managingBrokerId ) {
        this.managingBrokerId = managingBrokerId;
    }

    /**
     * Gets the mobile carrier.
     *
     * @return the mobile carrier
     */
    public String getMobileCarrier() {
        return mobileCarrier;
    }

    /**
     * Sets the mobile carrier.
     *
     * @param mobileCarrier
     *            the new mobile carrier
     */
    public void setMobileCarrier( final String mobileCarrier ) {
        this.mobileCarrier = mobileCarrier;
    }

    /**
     * Gets the agent starting date.
     *
     * @return the agent starting date
     */
    public String getAgentStartingDate() {
        return agentStartingDate;
    }

    /**
     * Sets the agent starting date.
     *
     * @param agentStartingDate
     *            the new agent starting date
     */
    public void setAgentStartingDate( final String agentStartingDate ) {
        this.agentStartingDate = agentStartingDate;
    }

    /**
     * Gets the notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes
     *            the new notes
     */
    public void setNotes( final String notes ) {
        this.notes = notes;
    }

    /**
     * Gets the driving radius.
     *
     * @return the driving radius
     */
    public String getDrivingRadius() {
        return drivingRadius;
    }

    /**
     * Sets the driving radius.
     *
     * @param drivingRadius
     *            the new driving radius
     */
    public void setDrivingRadius( final String drivingRadius ) {
        this.drivingRadius = drivingRadius;
    }

    /**
     * Gets the personal email.
     *
     * @return the personal email
     */
    public String getPersonalEmail() {
        return personalEmail;
    }

    /**
     * Sets the personal email.
     *
     * @param personalEmail
     *            the new personal email
     */
    public void setPersonalEmail( final String personalEmail ) {
        this.personalEmail = personalEmail;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public Double getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score
     *            the score to set
     */
    public void setScore( final Double score ) {
        this.score = score;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */

    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the type to set
     */
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * Gets the license.
     *
     * @return the license
     */
    public String getLicense() {
        return license;
    }

    /**
     * Sets the license.
     *
     * @param license
     *            the license to set
     */
    public void setLicense( final String license ) {
        this.license = license;
    }

    /**
     * Gets the language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     *
     * @param language
     *            the language to set
     */
    public void setLanguage( String language ) {
        this.language = language;
    }

    /**
     * Checks if is available.
     *
     * @return the isAvailable
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the available.
     *
     * @param isAvailable
     *            the isAvailable to set
     */
    public void setAvailable( final boolean isAvailable ) {
        this.isAvailable = isAvailable;
    }

    /**
     * Gets the revenue.
     *
     * @return the revenue
     */
    public Double getRevenue() {
        return revenue;
    }

    /**
     * Sets the revenue.
     *
     * @param revenue
     *            the new revenue
     */
    public void setRevenue( Double revenue ) {
        this.revenue = revenue;
    }

    /**
     * Gets the encodedPhotoData.
     *
     * @return the encodedPhotoData
     */
    public String getEncodedPhotoData() {
        return encodedPhotoData;
    }

    /**
     * Sets the encodedPhotoData.
     *
     * @param encodedPhotoData
     *            the new encodedPhotoData
     */
    public void setEncodedPhotoData(String encodedPhotoData) {
        this.encodedPhotoData = encodedPhotoData;
    }
    
    
}
