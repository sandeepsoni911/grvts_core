package com.owners.gravitas.dto.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ReminderRequest.
 *
 * @author harshads
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ReminderRequest {
	
	private String taskId;
	
	private  String opportunityId;
	
    public String getTaskId() {
		return taskId;
	}

    /** The trigger dtm. */
    @NotNull( message = "error.reminder.triggerDtm.required" )
    private Date triggerDtm;
    
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}


    /**
     * Gets the trigger dtm.
     *
     * @return the triggerDtm
     */
    public Date getTriggerDtm() {
        if (triggerDtm != null) {
            triggerDtm = ( Date ) triggerDtm.clone();
        }
        return triggerDtm;
    }

    /**
     * Sets the trigger dtm.
     *
     * @param triggerDtm
     *            the triggerDtm to set
     */
    public void setTriggerDtm( Date triggerDtm ) {
        this.triggerDtm = triggerDtm;
    }

	@Override
	public String toString() {
		return "ReminderRequest [taskId=" + taskId + ", opportunityId=" + opportunityId + ", triggerDtm=" + triggerDtm
				+ "]";
	}
    
}
