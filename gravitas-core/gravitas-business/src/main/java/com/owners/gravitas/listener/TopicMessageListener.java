package com.owners.gravitas.listener;

import static com.owners.gravitas.constants.CRMQuery.GET_RECORD_TYPE_NAME;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.REG_EXP_NON_BLANKSPACE_CHARS;
import static com.owners.gravitas.enums.EventType.ADD;
import static com.owners.gravitas.enums.EventType.UPDATE;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static org.apache.commons.lang.StringUtils.EMPTY;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.enums.EventType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The TopicMessageListener class.
 *
 * @author amits
 */
public abstract class TopicMessageListener implements MessageListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadTopicMessageListener.class );

    /** The mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /** The crm service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** The email exclusion prefix. */
    @Value( "${system.health.email.exclusion.prefix}" )
    private String emailExclusionPrefix;

    /** The email exclusion suffix. */
    @Value( "${system.health.email.exclusion.suffix}" )
    private String emailExclusionSuffix;

    /** The salesforce api username. */
    @Value( value = "${salesforce.api.user.fullname}" )
    protected String sfApiUserFullName;

    /**
     * Gets the data node.
     *
     * @param message
     *            the message
     * @return the data node
     */
    protected String getDataNode( final Message message ) {
        try {
            final JsonNode root = objectMapper.readTree( message.getJSON().getBytes("UTF-8") );
            return JsonUtil.toJson( root.path( "data" ) );
        } catch ( final IOException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
    }
    
    /**
     * Gets the object id.
     *
     * @param message
     *            the message
     * @return the object id
     */
    protected String getObjectId( final String message ) {
        JSONObject json = null;
        String objectId = null;
        try {
            json = ( JSONObject ) new JSONParser().parse( message );
            json = ( JSONObject ) json.get( "sobject" );
            objectId = String.valueOf( json.get( "Id" ) );

        } catch ( final ParseException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
        return objectId;
    }

    /**
     * Gets the object id.
     *
     * @param message
     *            the message
     * @return the object id
     */
    protected String getFieldValue( final String message, final String field ) {
        JSONObject json = null;
        String value = null;
        try {
            json = ( JSONObject ) new JSONParser().parse( message );
            json = ( JSONObject ) json.get( "sobject" );
            value = String.valueOf( json.get( field ) );

        } catch ( final ParseException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
        return value;
    }

    /**
     * Gets the salesforce object.
     *
     * @param message
     *            the message
     * @return the salesforce object
     */
    protected String getSalesforceObject( final String message ) {
        JSONObject json = null;
        try {
            json = ( JSONObject ) new JSONParser().parse( message );
            json = ( JSONObject ) json.get( "sobject" );
        } catch ( final ParseException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
        return json.toJSONString();
    }

    /**
     * Gets the event type.
     *
     * @param message
     *            the message
     * @return the event type
     */
    protected EventType getEventType( final String message ) {
        JSONObject type;
        try {
            type = ( JSONObject ) new JSONParser().parse( message );
        } catch ( final ParseException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
        final String eventType = String.valueOf( ( ( JSONObject ) type.get( "event" ) ).get( "type" ) );
        switch ( eventType ) {
            case "created":
                return ADD;
            case "updated":
                return UPDATE;
            default:
                return null;
        }
    }

    /**
     * Gets the record type id from json.
     *
     * @param dataNode
     *            the data node
     * @return the record type id from json
     */
    private String getRecordTypeIdFromJson( final String dataNode ) {
        try {
            final JsonNode dataObj = objectMapper.readTree( dataNode.getBytes() );
            return JsonUtil.toJson( dataObj.findValue( "RecordTypeId" ) ).replace( "\"", EMPTY );
        } catch ( final IOException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
    }

    /**
     * Creates the custom json.
     *
     * @param dataNode
     *            the data node
     * @return the string
     */
    protected String createCustomJSON( final String dataNode ) {
        final JsonParser parser = new JsonParser();
        final JsonObject json = parser.parse( dataNode ).getAsJsonObject();
        final JsonObject subject = parser.parse( json.get( "sobject" ).toString() ).getAsJsonObject();
        subject.remove( "RecordTypeId" );
        subject.addProperty( "RecordTypeName", getRecordTypeName( getRecordTypeIdFromJson( dataNode ) ) );
        json.remove( "sobject" );
        json.add( "sobject", subject );
        return String.valueOf( json );
    }

    /**
     * Gets the record type name.
     *
     * @param recordTypeId
     *            the record type id
     * @return the record type name
     */
    private String getRecordTypeName( final String recordTypeId ) {
        String recordTypeName = null;
        try {
            final QueryParams params = new QueryParams();
            params.add( ID, recordTypeId );
            recordTypeName = convertObjectToString(
                    crmQueryService.findOne( GET_RECORD_TYPE_NAME, params ).get( "Name" ) );
        } catch ( final ResultNotFoundException re ) {
            LOGGER.info( "Record Type Id does not exists. " + recordTypeId, re );
        }
        return recordTypeName;
    }

    /**
     * Checks whether we can process this record or not.
     *
     * @param emailId
     *            the email id which needs to be checked
     * @return true, if email id is different from that of the system health
     *         emails
     */
    protected boolean isSystemHealthCheckupEmail( final String emailId ) {
        return Pattern.matches( emailExclusionPrefix + REG_EXP_NON_BLANKSPACE_CHARS + emailExclusionSuffix, emailId );
    }

    /**
     * Checks whether we can process these records or not.
     *
     * @param emailId
     *            the email ids which needs to be checked
     * @return true, if email id is different from that of the system health
     *         emails
     */
    protected boolean canProcess( final List< String > emailIds ) {
        return emailIds.stream().noneMatch( emailId -> Pattern
                .matches( emailExclusionPrefix + REG_EXP_NON_BLANKSPACE_CHARS + emailExclusionSuffix, emailId ) );
    }

    /**
     * Checks whether we can process these records or not.
     *
     * @param emailIds
     *            the email ids which needs to be checked
     * @param lastModifiedBy
     *            the last modified by
     * @return true, if email id is different from that of the system health
     *         emails
     */
    protected boolean canProcess( final List< String > emailIds, final String lastModifiedBy ) {
        LOGGER.info( "LastModifiedBy: " + lastModifiedBy );
        boolean retVal = !sfApiUserFullName.equalsIgnoreCase( lastModifiedBy );
        if (retVal) {
            retVal = canProcess( emailIds );
        }
        LOGGER.info( "canProcess() : {} for emailIds : {}", retVal, emailIds );
        return retVal;
    }
}
