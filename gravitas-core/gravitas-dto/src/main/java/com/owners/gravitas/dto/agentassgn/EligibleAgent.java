package com.owners.gravitas.dto.agentassgn;

/**
 * The Class EligibleAgent.
 * 
 * @author ankusht
 */
public class EligibleAgent {

    /** The email. */
    private String email;

    /** The score. */
    private Double score;

    /** The name. */
    private String name;

    /** The phone. */
    private String phone;

    /**
     * Instantiates a new eligible agent.
     */
    public EligibleAgent() {
        super();
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
     *            the new phone
     */
    public void setPhone( final String phone ) {
        this.phone = phone;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( "EligibleAgent [email=" ).append( email ).append( ", score=" ).append( score ).append( "]" );
        return builder.toString();
    }
}
