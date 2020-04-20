package com.owners.gravitas.util;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.owners.gravitas.enums.ErrorCode.JSON_CONVERSION_ERROR;
import static com.owners.gravitas.enums.ErrorCode.OBJECT_CONVERSION_ERROR;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class JsonUtil.
 *
 * @author vishwanathm
 */
public final class JsonUtil {

    private JsonUtil() {
    }

    /** The Constant mapper. */
    final static ObjectMapper mapper = new ObjectMapper();

    /**
     * To json.
     *
     * @param <E>
     *            the element type
     * @param type
     *            the type
     * @return the string
     */
    public static < E > String toJson( final E type ) {
        String jsonValue = null;
        if (null != type) {
            mapper.setSerializationInclusion( NON_NULL );
            mapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
            try {
                jsonValue = mapper.writeValueAsString( type );
            } catch ( final JsonProcessingException e ) {
                throw new ApplicationException( JSON_CONVERSION_ERROR.getErrorDetail(), e, JSON_CONVERSION_ERROR );
            }
        }
        return jsonValue;
    }

    /**
     * To type.
     *
     * @param <T>
     *            the generic type
     * @param object
     *            the object
     * @param valueType
     *            the value type
     * @return the t
     */
    public static < T > T toType( final String jsonStr, final Class< T > valueType ) {
        T t = null;
        if (isNotBlank( jsonStr )) {
            try {
                t = mapper.readValue( jsonStr, valueType );
            } catch ( final IOException e ) {
                throw new ApplicationException( OBJECT_CONVERSION_ERROR.getErrorDetail(), e, OBJECT_CONVERSION_ERROR );
            }
        }
        return t;
    }

    /**
     * Convert Json to object
     * 
     * @param object
     * @param valueType
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static < T > T convertFromJson( final String object, final Class< T > valueType )
            throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        return mapper.readValue( object, valueType );
    }

    /**
     * Gets actual object from json, after removing metadata
     *
     * @param jsonData
     * @param clazz
     * @return
     * @throws ApplicationException
     */
    public static < T > T getFromJson( final String jsonData, final Class< T > clazz ) throws ApplicationException {
        T typeObject = null;
        try {
            if (jsonData.isEmpty()) {
                throw new ApplicationException( "Empty JSON String", ErrorCode.JSON_CONVERSION_ERROR );
            }
            final JsonParser jsonParser = new JsonParser();
            JsonObject responseObject = null;

            final JsonElement element = jsonParser.parse( jsonData );
            if (element.isJsonObject()) {
                responseObject = element.getAsJsonObject();

                JsonElement result = null;
                if (responseObject != null) {
                    result = responseObject.get( "result" );
                }
                if (result != null && !result.isJsonNull()) {
                    final String resultJsonString = result.toString();
                    typeObject = convertFromJson( resultJsonString, clazz );
                }
            }

        } catch ( final ApplicationException exception ) {
            throw exception;
        } catch ( final Exception exception ) {
            throw new ApplicationException( "We are getting some error while parsing json string", exception );
        }
        return typeObject;
    }

}
