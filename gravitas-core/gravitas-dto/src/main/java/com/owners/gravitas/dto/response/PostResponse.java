package com.owners.gravitas.dto.response;

/**
 * The Class AgentNoteResponse.
 */
public class PostResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1277226576616089271L;

    /** The name. */
    private String name;

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
    public void setName( String name ) {
        this.name = name;
    }
}
