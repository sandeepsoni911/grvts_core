package com.owners.gravitas.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class Stage.
 *
 * @author vishwanathm
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Stage extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4704122548009427056L;

    /** The stage. */
    private String stage;

    /** The timestamp. */
    private Long timestamp;

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage( final String stage ) {
        this.stage = stage;
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp
     *            the new timestamp
     */
    public void setTimestamp( final Long timestamp ) {
        this.timestamp = timestamp;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.domain.BaseDomain#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( "{stage=" ).append( stage ).append( ", timestamp=" ).append( timestamp ).append( "}" );
        return builder.toString();
    }
}
