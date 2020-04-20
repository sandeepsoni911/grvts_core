package com.owners.gravitas.dto;

/**
 * The Class AgentAssignmentLogDto.
 * 
 * @author ankusht, abhishek
 */
public class AgentAssignmentLogDto {

    /** The agent email. */
    private String agentEmail;

    /** The score. */
    private double score;

    /** The zip. */
    private String zip;

    /** The cbsa. */
    private String cbsa;

    /** The market label. */
    private String marketLabel;

    /**
     * Instantiates a new agent assignment log dto.
     *
     * @param agentEmail
     *            the agent email
     * @param score
     *            the score
     * @param zip
     *            the zip
     * @param cbsa
     *            the cbsa
     * @param marketLabel
     *            the market label
     */
    public AgentAssignmentLogDto( final String agentEmail, final double score, final String zip, final String cbsa,
            final String marketLabel ) {
        super();
        this.agentEmail = agentEmail;
        this.score = score;
        this.zip = zip;
        this.cbsa = cbsa;
        this.marketLabel = marketLabel;
    }

    /**
     * Gets the agent email.
     *
     * @return the agent email
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the agent email.
     *
     * @param agentEmail
     *            the new agent email
     */
    public void setAgentEmail( final String agentEmail ) {
        this.agentEmail = agentEmail;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score
     *            the new score
     */
    public void setScore( final double score ) {
        this.score = score;
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
     * Gets the cbsa.
     *
     * @return the cbsa
     */
    public String getCbsa() {
        return cbsa;
    }

    /**
     * Sets the cbsa.
     *
     * @param cbsa
     *            the new cbsa
     */
    public void setCbsa( final String cbsa ) {
        this.cbsa = cbsa;
    }

    /**
     * Gets the market label.
     *
     * @return the market label
     */
    public String getMarketLabel() {
        return marketLabel;
    }

    /**
     * Sets the market label.
     *
     * @param marketLabel
     *            the new market label
     */
    public void setMarketLabel( final String marketLabel ) {
        this.marketLabel = marketLabel;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( agentEmail == null ) ? 0 : agentEmail.hashCode() );
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
        final AgentAssignmentLogDto other = ( AgentAssignmentLogDto ) obj;
        if (agentEmail == null) {
            if (other.agentEmail != null) {
                return false;
            }
        } else if (!agentEmail.equals( other.agentEmail )) {
            return false;
        }
        return true;
    }
}
