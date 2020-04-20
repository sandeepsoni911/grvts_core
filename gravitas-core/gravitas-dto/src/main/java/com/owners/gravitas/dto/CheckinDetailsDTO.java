package com.owners.gravitas.dto;

import com.hubzu.notification.dto.model.BaseDto;

/**
 * The Class AgentCheckinDetailsDTO.
 *
 * @author amits
 */
public class CheckinDetailsDTO extends BaseDto {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5000345061246755581L;

    /** The user. */
    private User user;

    /** The opportunity details dto. */
    private OpportunityDetailsDTO opportunityDetailsDTO;

    /** The task checkin details dto. */
    private TaskCheckinDetailsDTO taskCheckinDetailsDTO;

    /**
     * Instantiates a new agent checkin details dto.
     */
    public CheckinDetailsDTO() {
        super();
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            the new user
     */
    public void setUser( User user ) {
        this.user = user;
    }

    /**
     * Gets the opportunity details dto.
     *
     * @return the opportunity details dto
     */
    public OpportunityDetailsDTO getOpportunityDetailsDTO() {
        return opportunityDetailsDTO;
    }

    /**
     * Sets the opportunity details dto.
     *
     * @param opportunityDetailsDTO
     *            the new opportunity details dto
     */
    public void setOpportunityDetailsDTO( OpportunityDetailsDTO opportunityDetailsDTO ) {
        this.opportunityDetailsDTO = opportunityDetailsDTO;
    }

    /**
     * Gets the task checkin details dto.
     *
     * @return the task checkin details dto
     */
    public TaskCheckinDetailsDTO getTaskCheckinDetailsDTO() {
        return taskCheckinDetailsDTO;
    }

    /**
     * Sets the task checkin details dto.
     *
     * @param taskCheckinDetailsDTO
     *            the new task checkin details dto
     */
    public void setTaskCheckinDetailsDTO( TaskCheckinDetailsDTO taskCheckinDetailsDTO ) {
        this.taskCheckinDetailsDTO = taskCheckinDetailsDTO;
    }

}
