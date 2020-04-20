/**
 *
 */
package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.BLANK;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.PropertyAddress;

/**
 * @author harshads
 *
 */
@Component
public class AddressFormatter {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( EmailUtil.class );

    /**
     * Format address.
     *
     * @param propertyAddress
     *            the property address
     * @return the string
     */
    public String formatAddress( final PropertyAddress propertyAddress ) {
        LOGGER.info( "formatting address started" );
        final StringBuilder formattedAddress = new StringBuilder();
        if (null != propertyAddress) {
            final String addressLine1 = propertyAddress.getAddressLine1();
            final String addressLine2 = propertyAddress.getAddressLine2();
            final String city = propertyAddress.getCity();
            final String state = propertyAddress.getState();
            final String zip = propertyAddress.getZip();

            formattedAddress.append( getFormattedLocation( addressLine1 ) )
                    .append( getFormattedLocation( addressLine2 ) ).append( getFormattedLocation( city ) )
                    .append( getFormattedLocation( state ) ).append( getFormattedLocation( zip ) );
        }
        LOGGER.info( "formatting address successful" );
        return formattedAddress.toString().trim();
    }

    /**
     * Gets the formatted location.
     *
     * @param location
     *            the location
     * @return the formatted location
     */
    private String getFormattedLocation( final String location ) {
        LOGGER.info( "given string is {}", location );
        return StringUtils.isNotBlank( location ) ? location + BLANK_SPACE : BLANK;
    }
}
