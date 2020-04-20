package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.util.DateUtil.DATE_TIME_PATTERN;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.GravitasHealthStatus;
import com.owners.gravitas.dto.SlackAttachment;
import com.owners.gravitas.dto.SlackAttachment.Field;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class SystemErrorSlackRequestBuilder.
 * 
 * @author ankusht
 */
@Component
public class SystemErrorSlackRequestBuilder extends AbstractBuilder< GravitasHealthStatus, SlackRequest > {

    /** The Constant SYSTEM_NAME. */
    private static final String SYSTEM_NAME = "System Name";

    /** The Constant ERROR_MESSAGE. */
    private static final String ERROR_MESSAGE = "Error Message";

    /** The Constant DESCRIPTION. */
    private static final String DESCRIPTION = "Description";

    /** The Constant ERROR_DATE_TIME_FIELD_LABEL. */
    private static final String ERROR_DATE_TIME_FIELD_LABEL = "Error Date Time";

    /** The Constant GRAVITAS_SYSTEM_DOWN_ERROR. */
    private static final String GRAVITAS_SYSTEM_DOWN_ERROR = "Gravitas System Down Error";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public SlackRequest convertTo( final GravitasHealthStatus source, final SlackRequest destination ) {
        SlackRequest errorRequest = destination;
        if (source != null) {
            if (errorRequest == null) {
                errorRequest = new SlackRequest();
            }
            errorRequest.setUsername( GRAVITAS );
            errorRequest.addAttachments( populateSlackAttachment( source ) );
        }
        return errorRequest;
    }

    /**
     * Populate slack attachment.
     *
     * @param source
     *            the source
     * @return the slack attachment
     */
    private SlackAttachment populateSlackAttachment( final GravitasHealthStatus source ) {
        final SlackAttachment slackAttachment = new SlackAttachment();
        slackAttachment.setTitle( GRAVITAS_SYSTEM_DOWN_ERROR );
        slackAttachment.addField( createField( ERROR_DATE_TIME_FIELD_LABEL,
                DateUtil.toString( new DateTime(), DATE_TIME_PATTERN ), slackAttachment ) );
        slackAttachment.addField( createField( SYSTEM_NAME, source.getSystemName(), slackAttachment ) );
        slackAttachment.addField( createField( ERROR_MESSAGE, source.getMessage(), slackAttachment ) );
        slackAttachment.addField( createField( DESCRIPTION, source.getFailureInfo(), slackAttachment ) );
        return slackAttachment;
    }

    /**
     * Creates the field.
     *
     * @param title
     *            the title
     * @param value
     *            the value
     * @param slackAttachment
     *            the slack attachment
     * @return the field
     */
    private Field createField( final String title, final String value, final SlackAttachment slackAttachment ) {
        final Field field = slackAttachment.new Field();
        field.setTitle( title );
        field.setValue( value );
        return field;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public GravitasHealthStatus convertFrom( final SlackRequest source, final GravitasHealthStatus destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
