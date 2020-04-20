package com.owners.gravitas.enums;

/**
 * The Enum PlatformVersion.
 *
 * @author amits
 */
public enum PlatformVersion {

    /** The VERSIO n_1_0_3. */
    VERSION_1_0_3( "1.0.3" );

    /** The version. */
    private String version;

    /**
     * Instantiates a new platform version.
     *
     * @param version
     *            the version
     */
    private PlatformVersion( final String version ) {
        this.version = version;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

}
