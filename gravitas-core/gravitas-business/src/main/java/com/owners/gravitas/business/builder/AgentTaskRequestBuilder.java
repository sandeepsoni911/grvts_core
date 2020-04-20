package com.owners.gravitas.business.builder;

import static com.owners.gravitas.enums.AgentTaskStatus.CONFIRMED;
import static com.owners.gravitas.enums.AgentTaskStatus.PENDING;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.request.AgentMeetingRequest;
import com.owners.gravitas.dto.request.AgentTaskRequest;

/**
 * The Class AgentTaskRequestBuilder.
 *
 * @author bhardrah
 */
@Component
public class AgentTaskRequestBuilder extends AbstractBuilder< AgentMeetingRequest, AgentTaskRequest > {
	
	 /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentTaskRequestBuilder.class );
	
    private static final String createdBy = "Inside Sales";
    
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentTaskRequest convertTo( final AgentMeetingRequest source, final AgentTaskRequest destination ) {
        AgentTaskRequest agentTaskRequest = destination;
        if (source != null) {
            if (agentTaskRequest == null) {
                agentTaskRequest = new AgentTaskRequest();
            }
            agentTaskRequest.setDueDtm(getDueDateFromStringDt(source.getDueDtm(), source.getTimezone()));
            agentTaskRequest.setCreatedBy( createdBy );
            agentTaskRequest.setDescription( source.getDescription() );
            agentTaskRequest.setLocation( source.getLocation() );
            agentTaskRequest.setStatus( null!=source.getIsWarmTransferCall() && source.getIsWarmTransferCall()?CONFIRMED.name(): PENDING.name());
            agentTaskRequest.setTitle( source.getTitle() );
            agentTaskRequest.setType( source.getType() );
            agentTaskRequest.setIsWarmTransferCall(source.getIsWarmTransferCall());
        }
        return agentTaskRequest;
    }

    /**
     * To get date object
     * from string date time and
     * Time zone
     * @param dueDtm
     * @return
     */
    private Date getDueDateFromStringDt(final String dueDtm, final String timezone) {
         Date dueDate = null;
			try {
				final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");
				sdf.setTimeZone(TimeZone.getTimeZone(timezone));
				dueDate = sdf.parse(dueDtm);
			} catch (final Exception e) {
				LOGGER.error("Exception while converstion of date : {}, timezone : {}", dueDtm, timezone, e);
			}  
		return dueDate;
	}

	/*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AgentMeetingRequest convertFrom( final AgentTaskRequest source, final AgentMeetingRequest destination ) {
        throw new UnsupportedOperationException();
    }

}
