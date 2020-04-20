package com.owners.gravitas.amqp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ListingIdInfo.
 *
 * @author pabhishek
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ListingIdInfo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3485532812623181262L;

}
