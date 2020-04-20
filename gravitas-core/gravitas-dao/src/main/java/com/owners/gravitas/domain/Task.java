package com.owners.gravitas.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.owners.gravitas.dto.TourDetails;

// TODO: Auto-generated Javadoc
/**
 * The Class Task as domain.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Task extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1695988069368714998L;

    /** The task id. */
    private String id;

    /** The opportunity id. */
    private String opportunityId;

    /** The title. */
    private String title;

    private String tag;

    /** The description. */
    private String description;

    /** The task type. */
    private String taskType;

    /** The due dtm. */
    private Long dueDtm;

    /** The location. */
    private String location;

    /** The created dtm. */
    private Long createdDtm;

    /** The created by. */
    private String createdBy;

    /** The deleted by. */
    private String deletedBy;

    /** The completed dtm. */
    private Long completedDtm;

    /** The completed by. */
    private String completedBy;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /** The request id. */
    private String requestId;

    /** The deleted. */
    private Boolean deleted = Boolean.FALSE;

    /** The last viewed dtm. */
    private Long lastViewedDtm;

    /** The reminders. */
    private Map< String, Reminder > reminders;

    /** The agent task status. */
    @JsonInclude( JsonInclude.Include.NON_EMPTY )
    private String status;

    /** The cancellation reason. */
    private String cancellationReason;

    /** The is primary. */
    private Boolean isPrimary = Boolean.FALSE;

    /** The co shoppind Id. */
    private String coShoppingId;

    /** The dates. */
    private List< List< TourDetails > > dates;

    /** The listing id. */
    private String listingId;

    /** The warm call transfer flag */
    private Boolean isWarmTransferCall;

    /**
     * Instantiates a new task.
     */
    public Task() {
        // do nothing
    }

    /**
     * Instantiates a new task.
     *
     * @param title
     *            the title
     * @param opportunityId
     *            the opportunity id
     * @param taskType
     *            the task type
     * @param createdBy
     *            the created by
     * @param createdDtm
     *            the created dtm
     */
    public Task( final String title, final String opportunityId, final String taskType, final String createdBy,
            final Long createdDtm ) {
        this.opportunityId = opportunityId;
        this.title = title;
        this.taskType = taskType;
        this.createdDtm = createdDtm;
        this.createdBy = createdBy;
        this.lastModifiedDtm = createdDtm;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id to set
     */
    public void setId( final String id ) {
        this.id = id;
    }

    /**
     * Gets the opportunity id.
     *
     * @return the opportunity id
     */
    public String getOpportunityId() {
        return opportunityId;
    }

    /**
     * Sets the opportunity id.
     *
     * @param opportunityId
     *            the new opportunity id
     */
    public void setOpportunityId( final String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public void setTitle( final String title ) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag( final String tag ) {
        this.tag = tag;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription( final String description ) {
        this.description = description;
    }

    /**
     * Gets the task type.
     *
     * @return the taskType
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Sets the task type.
     *
     * @param taskType
     *            the taskType to set
     */
    public void setTaskType( final String taskType ) {
        this.taskType = taskType;
    }

    /**
     * Gets the due dtm.
     *
     * @return the due dtm
     */
    public Long getDueDtm() {
        return dueDtm;
    }

    /**
     * Sets the due dtm.
     *
     * @param dueDtm
     *            the new due dtm
     */
    public void setDueDtm( final Long dueDtm ) {
        this.dueDtm = dueDtm;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location
     *            the new location
     */
    public void setLocation( final String location ) {
        this.location = location;
    }

    /**
     * Gets the created dtm.
     *
     * @return the created dtm
     */
    public Long getCreatedDtm() {
        return createdDtm;
    }

    /**
     * Sets the created dtm.
     *
     * @param createdDtm
     *            the new created dtm
     */
    public void setCreatedDtm( final Long createdDtm ) {
        this.createdDtm = createdDtm;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the completed dtm.
     *
     * @return the completed dtm
     */
    public Long getCompletedDtm() {
        return completedDtm;
    }

    /**
     * Sets the completed dtm.
     *
     * @param completedDtm
     *            the new completed dtm
     */
    public void setCompletedDtm( final Long completedDtm ) {
        this.completedDtm = completedDtm;
    }

    /**
     * Gets the completed by.
     *
     * @return the completed by
     */
    public String getCompletedBy() {
        return completedBy;
    }

    /**
     * Sets the completed by.
     *
     * @param completedBy
     *            the new completed by
     */
    public void setCompletedBy( final String completedBy ) {
        this.completedBy = completedBy;
    }

    /**
     * Gets the last modified dtm.
     *
     * @return the last modified dtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * Sets the last modified dtm.
     *
     * @param lastModifiedDtm
     *            the new last modified dtm
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /**
     * Gets the request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the request id.
     *
     * @param requestId
     *            the new request id
     */
    public void setRequestId( final String requestId ) {
        this.requestId = requestId;
    }

    /**
     * Gets the deleted.
     *
     * @return the deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted( final Boolean deleted ) {
        this.deleted = deleted;
    }

    /**
     * Gets the last viewed dtm.
     *
     * @return the last viewed dtm
     */
    public Long getLastViewedDtm() {
        return lastViewedDtm;
    }

    /**
     * Sets the last viewed dtm.
     *
     * @param lastViewedDtm
     *            the new last viewed dtm
     */
    public void setLastViewedDtm( final Long lastViewedDtm ) {
        this.lastViewedDtm = lastViewedDtm;
    }

    /**
     * Gets the reminders.
     *
     * @return the reminders
     */
    public Map< String, Reminder > getReminders() {
        return reminders;
    }

    /**
     * Sets the reminders.
     *
     * @param reminders
     *            the new reminders
     */
    public void setReminders( final Map< String, Reminder > reminders ) {
        this.reminders = reminders;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * Gets the cancellation reason.
     *
     * @return the cancellationReason
     */
    public String getCancellationReason() {
        return cancellationReason;
    }

    /**
     * Sets the cancellation reason.
     *
     * @param cancellationReason
     *            the cancellationReason to set
     */
    public void setCancellationReason( final String cancellationReason ) {
        this.cancellationReason = cancellationReason;
    }

    /**
     * Gets the checks if is primary.
     *
     * @return the isPrimary
     */
    public Boolean getIsPrimary() {
        return isPrimary;
    }

    /**
     * Sets the checks if is primary.
     *
     * @param isPrimary
     *            the isPrimary to set
     */
    public void setIsPrimary( final Boolean isPrimary ) {
        this.isPrimary = isPrimary;
    }

    /**
     * Gets the co shopping id.
     *
     * @return the coShoppingId
     */
    public String getCoShoppingId() {
        return coShoppingId;
    }

    /**
     * Sets the co shopping id.
     *
     * @param coShoppingId
     *            the coShoppingId to set
     */
    public void setCoShoppingId( final String coShoppingId ) {
        this.coShoppingId = coShoppingId;
    }

    /**
     * @return the TourDetails
     */
    public List< List< TourDetails > > getDates() {
        return dates;
    }

    /**
     * @param TourDetails
     *            the TourDetails to set
     */
    public void setDates( final List< List< TourDetails > > dates ) {
        this.dates = dates;
    }

    /**
     * Gets the listing id.
     *
     * @return the listing id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the new listing id
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
    }

    /**
     * @return the isWarmTransferCall
     */
    public Boolean getIsWarmTransferCall() {
        return isWarmTransferCall;
    }

    /**
     * @param isWarmTransferCall
     *            the isWarmTransferCall to set
     */
    public void setIsWarmTransferCall( final Boolean isWarmTransferCall ) {
        this.isWarmTransferCall = isWarmTransferCall;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy( final String deletedBy ) {
        this.deletedBy = deletedBy;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.domain.BaseDomain#toString()
     */
    @Override
    public String toString() {
        return "Task [id=" + id + ", opportunityId=" + opportunityId + ", title=" + title + ", tag=" + tag
                + ", description=" + description + ", taskType=" + taskType + ", dueDtm=" + dueDtm + ", location="
                + location + ", createdDtm=" + createdDtm + ", createdBy=" + createdBy + ", deletedBy=" + deletedBy
                + ", completedDtm=" + completedDtm + ", completedBy=" + completedBy + ", lastModifiedDtm="
                + lastModifiedDtm + ", requestId=" + requestId + ", deleted=" + deleted + ", lastViewedDtm="
                + lastViewedDtm + ", reminders=" + reminders + ", status=" + status + ", cancellationReason="
                + cancellationReason + ", isPrimary=" + isPrimary + ", coShoppingId=" + coShoppingId + ", dates="
                + dates + ", listingId=" + listingId + ", isWarmTransferCall=" + isWarmTransferCall + "]";
    }
}
