package com.owners.gravitas.serializer;

import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.owners.gravitas.util.DateUtil;

/**
 * Handles date object serialization.
 *
 * @author vishwanathm
 *
 */
public class CustomDateDeserializer extends JsonDeserializer< DateTime > {

    /**
     * Handles date deserialization.
     *
     * @param jparser
     *            JsonParser instance
     * @param ctxt
     *            context
     * @throws IOException
     *             Error in date conversion
     */
    public DateTime deserialize( final JsonParser jparser, final DeserializationContext ctxt ) throws IOException {
        final String text = jparser.getText();
        return DateUtil.toDateTime( text, DEFAULT_CRM_DATE_PATTERN );
    }

}
