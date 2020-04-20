package com.owners.gravitas.config;

/**
 * The Class ApiConfig.
 *
 * @author vishwanathm
 */
public class ApiConfig {

    /** The api key. */
    private String apiKey;

    /** The api secret. */
    private String apiSecret;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /**
     * Instantiates a new api config.
     *
     * @param apiKey
     *            the api key
     * @param apiSecret
     *            the api secret
     * @param username
     *            the username
     * @param password
     *            the password
     */
    public ApiConfig( final String apiKey, final String apiSecret, final String username, final String password ) {

        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the api key.
     *
     * @param apiKey
     *            the new api key
     */
    public void setApiKey( String apiKey ) {
        this.apiKey = apiKey;
    }

    /**
     * Gets the api secret.
     *
     * @return the api secret
     */
    public String getApiSecret() {
        return apiSecret;
    }

    /**
     * Sets the api secret.
     *
     * @param apiSecret
     *            the new api secret
     */
    public void setApiSecret( String apiSecret ) {
        this.apiSecret = apiSecret;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username
     *            the new username
     */
    public void setUsername( String username ) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    public void setPassword( String password ) {
        this.password = password;
    }

}
