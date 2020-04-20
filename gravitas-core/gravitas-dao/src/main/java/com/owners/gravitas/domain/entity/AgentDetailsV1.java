package com.owners.gravitas.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Class CubeAgentEligibleZips.
 */
@Entity( name = "CUBE_AGENT_ELIGIBLE_ZIPS_V1" )
public class AgentDetailsV1 implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -316593200458698295L;

    /** The agent name. */
    @Column( name = "AGENT_NAME" )
    private String agentName;

    /** The email. */
    @Id
    /** The email. */
    @Column( name = "EMAIL" )
    private String email;

    /** The status. */
    @Column( name = "STATUS" )
    private String status;

    /** The phone. */
    @Column( name = "PHONE" )
    private String phone;

    /** The state. */
    @Column( name = "STATE" )
    private String state;

    /** The home zip. */
    @Column( name = "HOME_ZIP" )
    private String homeZip;

    /** The zip. */
    @Column( name = "ZIP" )
    private String zip;

    /** The distance. */
    @Column( name = "DISTANCE" )
    private String distance;

    /** The name. */
    @Column( name = "NAME" )
    private String name;

    /** The must keep. */
    @Column( name = "MUST_KEEP" )
    private String mustKeep;

    /** The do not default milage. */
    @Column( name = "DO_NOT_DEFAULT_MILEAGE" )
    private String doNotDefaultMilage;

    /** The score. */
    private String score;

    /** The language. */
    private String language;

    /**
     * Gets the agent name.
     *
     * @return the agentName
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the agent name.
     *
     * @param agentName
     *            the agentName to set
     */
    public void setAgentName( String agentName ) {
        this.agentName = agentName;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the email to set
     */
    public void setEmail( String email ) {
        this.email = email;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the status to set
     */
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone
     *            the phone to set
     */
    public void setPhone( String phone ) {
        this.phone = phone;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the state to set
     */
    public void setState( String state ) {
        this.state = state;
    }

    /**
     * Gets the home zip.
     *
     * @return the homeZip
     */
    public String getHomeZip() {
        return homeZip;
    }

    /**
     * Sets the home zip.
     *
     * @param homeZip
     *            the homeZip to set
     */
    public void setHomeZip( String homeZip ) {
        this.homeZip = homeZip;
    }

    /**
     * Gets the zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the zip.
     *
     * @param zip
     *            the zip to set
     */
    public void setZip( String zip ) {
        this.zip = zip;
    }

    /**
     * Gets the distance.
     *
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Sets the distance.
     *
     * @param distance
     *            the distance to set
     */
    public void setDistance( String distance ) {
        this.distance = distance;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name to set
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Gets the must keep.
     *
     * @return the mustKeep
     */
    public String getMustKeep() {
        return mustKeep;
    }

    /**
     * Sets the must keep.
     *
     * @param mustKeep
     *            the mustKeep to set
     */
    public void setMustKeep( String mustKeep ) {
        this.mustKeep = mustKeep;
    }

    /**
     * Gets the do not default milage.
     *
     * @return the doNotDefaultMilage
     */
    public String getDoNotDefaultMilage() {
        return doNotDefaultMilage;
    }

    /**
     * Sets the do not default milage.
     *
     * @param doNotDefaultMilage
     *            the doNotDefaultMilage to set
     */
    public void setDoNotDefaultMilage( String doNotDefaultMilage ) {
        this.doNotDefaultMilage = doNotDefaultMilage;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public String getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score
     *            the score to set
     */
    public void setScore( String score ) {
        this.score = score;
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
}
