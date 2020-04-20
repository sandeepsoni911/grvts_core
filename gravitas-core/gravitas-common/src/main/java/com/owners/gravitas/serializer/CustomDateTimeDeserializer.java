package com.owners.gravitas.serializer;

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
public class CustomDateTimeDeserializer extends JsonDeserializer< DateTime > {

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
        return DateUtil.toDateTime( text, DateUtil.CRM_DATE_TIME_PATTERN_OFFSET );
    }

}
