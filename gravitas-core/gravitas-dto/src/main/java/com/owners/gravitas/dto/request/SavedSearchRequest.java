package com.owners.gravitas.dto.request;

/**
 * The Class SavedSearchRequest.
 *
 * @author pabhishek
 */
public class SavedSearchRequest {

    /** The uuid. */
    private String uuid;

    /** The zip. */
    private String zip;

    /** The city. */
    private String city;

    /** The state. */
    private String state;

    /** The beds. */
    private int beds;

    /** The price. */
    private String price;

    /** The property type. */
    private String propertyType;

    /** The mls id. */
    private String mlsId;

    /** The address. */
    private String address;

    /**
     * Instantiates a new saved search request.
     */
    public SavedSearchRequest() {

    }

    /**
     * Instantiates a new saved search request.
     *
     * @param uuid
     *            the uuid
     * @param zip
     *            the zip
     * @param city
     *            the city
     * @param state
     *            the state
     * @param beds
     *            the beds
     * @param price
     *            the price
     * @param propertyType
     *            the property type
     * @param mlsId
     *            the mls id
     * @param address
     *            the address
     */
    public SavedSearchRequest( final String uuid, final String zip, final String city, final String state,
            final int beds, final String price, final String propertyType, final String mlsId, final String address ) {
        super();
        this.uuid = uuid;
        this.zip = zip;
        this.city = city;
        this.state = state;
        this.beds = beds;
        this.price = price;
        this.propertyType = propertyType;
        this.mlsId = mlsId;
        this.address = address;
    }

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid.
     *
     * @param uuid
     *            the new uuid
     */
    public void setUuid( final String uuid ) {
        this.uuid = uuid;
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
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city
     *            the new city
     */
    public void setCity( final String city ) {
        this.city = city;
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

    /**
     * Gets the beds.
     *
     * @return the beds
     */
    public int getBeds() {
        return beds;
    }

    /**
     * Sets the beds.
     *
     * @param beds
     *            the new beds
     */
    public void setBeds( final int beds ) {
        this.beds = beds;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price
     *            the new price
     */
    public void setPrice( final String price ) {
        this.price = price;
    }

    /**
     * Gets the property type.
     *
     * @return the property type
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * Sets the property type.
     *
     * @param propertyType
     *            the new property type
     */
    public void setPropertyType( final String propertyType ) {
        this.propertyType = propertyType;
    }

    /**
     * Gets the mls id.
     *
     * @return the mls id
     */
    public String getMlsId() {
        return mlsId;
    }

    /**
     * Sets the mls id.
     *
     * @param mlsId
     *            the new mls id
     */
    public void setMlsId( final String mlsId ) {
        this.mlsId = mlsId;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address
     *            the new address
     */
    public void setAddress( final String address ) {
        this.address = address;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SavedSearchRequest [uuid=" + uuid + ", zip=" + zip + ", city=" + city + ", state=" + state + ", beds="
                + beds + ", price=" + price + ", propertyType=" + propertyType + ", mlsId=" + mlsId + ", address="
                + address + "]";
    }

}
