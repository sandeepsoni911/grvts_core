/**
 *
 */
package com.owners.gravitas.dto;

/**
 * The Class TourRange.
 *
 * @author harshads
 */
public class TourRange extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7259448790142625213L;

    /** The start. */
    private String start;

    /** The end. */
    private String end;

    /**
     * Gets the start.
     *
     * @return the start
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start.
     *
     * @param start
     *            the new start
     */
    public void setStart( final String start ) {
        this.start = start;
    }

    /**
     * Gets the end.
     *
     * @return the end
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end.
     *
     * @param end
     *            the new end
     */
    public void setEnd( final String end ) {
        this.end = end;
    }
}
