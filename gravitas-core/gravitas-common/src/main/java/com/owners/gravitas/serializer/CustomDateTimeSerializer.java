package com.owners.gravitas.serializer;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.owners.gravitas.util.DateUtil;

/**
 * Handles date object serialization.
 *
 * @author vishwanathm
 *
 */
public class CustomDateTimeSerializer extends JsonSerializer< DateTime > {

    /**
     * Handles date serialization.
     *
     * @param date
     *            {@link DateTime} instance
     * @param gen
     *            {@link JsonGenerator} instance.
     * @param provider
     *            {@link SerializerProvider} instance.
     * @throws IOException
     *             Error in date conversion
     * @throws JsonProcessingException
     *
     */
    @Override
    public void serialize( final DateTime date, final JsonGenerator gen, final SerializerProvider provider )
            throws IOException {
        gen.writeString( DateUtil.toString( date, DateUtil.CRM_DATE_TIME_PATTERN_OFFSET ) );
    }
}
