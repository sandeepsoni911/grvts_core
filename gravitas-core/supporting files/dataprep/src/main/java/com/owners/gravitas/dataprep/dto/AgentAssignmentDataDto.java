package com.owners.gravitas.dataprep.dto;

/**
 * The Class AgentAssignmentDataDto.
 * 
 * @author ankusht
 */
public class AgentAssignmentDataDto {

    /** The email. */
    private String email;

    /** The first name. */
    private String name;

    /** The score. */
    private Double score;

    /** The home zip. */
    private String homeZip;

    /** The zip. */
    private String zip;

    /** The state. */
    private String state;

    /**
     * Instantiates a new agent assignment data dto.
     */
    public AgentAssignmentDataDto() {
        super();
    }

    /**
     * Instantiates a new agent assignment data dto.
     *
     * @param email
     *            the email
     * @param name
     *            the name
     * @param score
     *            the score
     * @param homeZip
     *            the home zip
     * @param zip
     *            the zip
     * @param state
     *            the state
     */
    public AgentAssignmentDataDto( final String email, final String name, final Double score, final String homeZip,
            final String zip, final String state ) {
        super();
        this.email = email;
        this.name = name;
        this.score = score;
        this.homeZip = homeZip;
        this.zip = zip;
        this.state = state;
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
     *            the new email
     */
    public void setEmail( final String email ) {
        this.email = email;
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
     *            the new name
     */
    public void setName( final String name ) {
        this.name = name;
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
     *            the new score
     */
    public void setScore( final Double score ) {
        this.score = score;
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
     *            the new zip
     */
    public void setZip( final String zip ) {
        this.zip = zip;
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
     *            the new state
     */
    public void setState( final String state ) {
        this.state = state;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( email ).append( "=> score=" ).append( score ).append( ", zip=" ).append( zip )
                .append( ", state=" ).append( state );
        return builder.toString();
    }
}
