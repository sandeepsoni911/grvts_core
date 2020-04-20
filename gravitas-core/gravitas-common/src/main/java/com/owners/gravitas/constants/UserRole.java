package com.owners.gravitas.constants;

/**
 * The Class UserRole groups the user roles.
 * Note: Always prefix user role with <b>ROLE_</b> as this is required for
 * spring security.
 */
public class UserRole {

    /**
     * Instantiates a new user role.
     */
    private UserRole() {
    }

    /** The Constant AGENT. */
    public static final String AGENT = "ROLE_1099_AGENT";

    /** The Constant FIREBASE_AGENT. */
    public static final String FIREBASE_AGENT = "1099_AGENT";

    /** The Constant ADMIN. */
    public static final String ADMIN = "ROLE_ADMIN";

    /** The Constant ADMIN_USER. */
    public static final String ADMIN_USER = "ADMIN";

    /** The Constant ADMIN. */
    public static final String INSIDE_SALES = "ROLE_INSIDE_SALES";

    /** The Constant MANAGING_BROKER. */
    public static final String MANAGING_BROKER = "MANAGING_BROKER";

    /** The Constant AUTHORITY_MANAGING_BROKER. */
    public static final String AUTHORITY_MANAGING_BROKER = "ROLE_MANAGING_BROKER";
}
