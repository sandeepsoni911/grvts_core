package com.owners.gravitas.domain;

/**
 * The Class Device.
 *
 * @author amits
 */
public class Device extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7399117920930253277L;

    /** The type. */
    private String type;

    /** The id. */
    private String id;

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
     *            the new type
     */
    public void setType( String type ) {
        this.type = type;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId( String id ) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj ) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Device other = ( Device ) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals( other.id ))
            return false;
        return true;
    }

}
