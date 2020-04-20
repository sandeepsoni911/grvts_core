package com.owners.gravitas.domain.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * The Class AgentDetails.
 *
 * @author pabhishek
 */
@Entity( name = "GR_AGENT_DETAIL" )
public class AgentDetails extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6608441349637116204L;

    /** The agent coverage. */
    @OneToMany( cascade = CascadeType.ALL )
    @JoinColumn( name = "AGENT_DETAIL_ID", nullable = false, referencedColumnName = "ID" )
    private List< AgentCoverage > coverageArea;

    /** The user. */
    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "USER_ID" )
    private User user;

    /** The managing broker. */
    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "MANAGING_BROKER_ID" )
    private User managingBroker;

    /** The mobile carrier. */
    @Column( name = "MOBILE_CARRIER", nullable = true )
    private String mobileCarrier;

    /** The language. */
    @Column( name = "LANGUAGE", nullable = true )
    private String language;

    /** The starting on. */
    @Column( name = "STARTING_ON", nullable = true )
    private Date startingOn;

    /** The ending on. */
    @Column( name = "ENDING_ON", nullable = true )
    private Date endingOn;

    /** The driving radius. */
    @Column( name = "DRIVING_RADIUS", nullable = true )
    private int drivingRadius;

    /** The availability. */
    @Column( name = "AVAILABILITY", nullable = false )
    private boolean availability;

    /** The home zip. */
    @Column( name = "HOME_ZIP", nullable = true )
    private String homeZip;

    /** The score. */
    @Column( name = "SCORE", nullable = true )
    private Double score;

    /** The state. */
    @Column( name = "HOME_STATE", nullable = true )
    private String state;

    /** The license. */
    @Column( name = "LICENSE", nullable = true )
    private String license;

    /**
     * Instantiates a new agent details.
     */
    public AgentDetails() {

    }

    /**
     * Instantiates a new agent details.
     *
     * @param coverageArea
     *            the coverage area
     * @param user
     *            the user
     * @param managingBroker
     *            the managing broker
     * @param mobileCarrier
     *            the mobile carrier
     * @param startingOn
     *            the starting on
     * @param endingOn
     *            the ending on
     * @param drivingRadius
     *            the driving radius
     * @param availability
     *            the availability
     * @param homeZip
     *            the home zip
     * @param state
     *            the state
     * @param license
     *            the license
     * @param language
     *            the language
     */

    public AgentDetails( final List< AgentCoverage > coverageArea, final User user, final User managingBroker,
            final String mobileCarrier, final Date startingOn, final Date endingOn, final int drivingRadius,
            final boolean availability, final String homeZip, final String state, final String license,
            final String language ) {
        super();
        this.coverageArea = coverageArea;
        this.user = user;
        this.managingBroker = managingBroker;
        this.mobileCarrier = mobileCarrier;
        this.startingOn = startingOn;
        this.endingOn = endingOn;
        this.drivingRadius = drivingRadius;
        this.availability = availability;
        this.homeZip = homeZip;
        this.state = state;
        this.license = license;
        this.language = language;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            the new user
     */
    public void setUser( final User user ) {
        this.user = user;
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
    public void setLanguage( final String language ) {
        this.language = language;
    }

    /**
     * Gets the driving radius.
     *
     * @return the driving radius
     */
    public int getDrivingRadius() {
        return drivingRadius;
    }

    /**
     * Sets the driving radius.
     *
     * @param drivingRadius
     *            the new driving radius
     */
    public void setDrivingRadius( final int drivingRadius ) {
        this.drivingRadius = drivingRadius;
    }

    /**
     * Gets the agent coverage.
     *
     * @return the agent coverage
     */
    public List< AgentCoverage > getCoverageArea() {
        return coverageArea;
    }

    /**
     * Sets the agent coverage.
     *
     * @param agentCoverage
     *            the new coverage area
     */
    public void setCoverageArea( final List< AgentCoverage > agentCoverage ) {
        this.coverageArea = agentCoverage;
    }

    /**
     * Gets the managing broker.
     *
     * @return the managing broker
     */
    public User getManagingBroker() {
        return managingBroker;
    }

    /**
     * Sets the managing broker.
     *
     * @param managingBroker
     *            the new managing broker
     */
    public void setManagingBroker( final User managingBroker ) {
        this.managingBroker = managingBroker;
    }

    /**
     * Gets the starting on.
     *
     * @return the starting on
     */
    public Date getStartingOn() {
        return startingOn;
    }

    /**
     * Sets the starting on.
     *
     * @param startingOn
     *            the new starting on
     */
    public void setStartingOn( final Date startingOn ) {
        this.startingOn = startingOn;
    }

    /**
     * Gets the ending on.
     *
     * @return the ending on
     */
    public Date getEndingOn() {
        return endingOn;
    }

    /**
     * Sets the ending on.
     *
     * @param endingOn
     *            the new ending on
     */
    public void setEndingOn( final Date endingOn ) {
        this.endingOn = endingOn;
    }

    /**
     * Checks if is availability.
     *
     * @return true, if is availability
     */
    public boolean isAvailability() {
        return availability;
    }

    /**
     * Sets the availability.
     *
     * @param availability
     *            the new availability
     */
    public void setAvailability( final boolean availability ) {
        this.availability = availability;
    }

    /**
     * Gets the home zip.
     *
     * @return the home zip
     */
    public String getHomeZip() {
        return homeZip;
    }

    /**
     * Sets the home zip.
     *
     * @param homeZip
     *            the new home zip
     */
    public void setHomeZip( final String homeZip ) {
        this.homeZip = homeZip;
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
    public void setState( final String state ) {
        this.state = state;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( getId() == null ) ? 0 : getId().hashCode() );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AgentDetails other = ( AgentDetails ) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals( other.getId() )) {
            return false;
        }
        return true;
    }
}
