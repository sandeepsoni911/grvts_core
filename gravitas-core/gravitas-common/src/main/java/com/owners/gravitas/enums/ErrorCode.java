package com.owners.gravitas.enums;

/**
 * Bundles error codes.
 *
 * @author vishwanathm
 */
public enum ErrorCode {

    /** internal server error. */
    INTERNAL_SERVER_ERROR( "system.error", "System error occurred" ),

    /** The access denied error. */
    ACCESS_DENIED_ERROR( "access.denied", "Access is denied" ),

    /** invalid format error. */
    INVALID_FORMAT_ERROR( "invalid.format.error", "Input format is not valid" ),

    /** The json conversion error. */
    JSON_CONVERSION_ERROR( "json.conversion.error", "Object to Json transformation failed." ),

    /** The object conversion error. */
    OBJECT_CONVERSION_ERROR( "object.conversion.error", "Json to object transformation failed." ),

    /** The opportunity already exists. */
    OPPORTUNITY_ALREADY_EXISTS( "cmr.opportunity.exists", "Opportunity already exists" ),

    /** The owner not found. */
    OWNER_NOT_FOUND( "crm.owner.not.found", "CRM Owner id not found." ),

    /** The result not found. */
    RESULT_NOT_FOUND( "crm.result.not.found", "Result not found." ),

    /** The agent not found. */
    AGENT_NOT_FOUND( "fb.agent.not.found", "Agent not found." ),

    /** The agent not valid. */
    AGENT_NOT_VALID( "fb.agent.not.valid", "Agent is not valid." ),

    /** The agent opportunity not found. */
    AGENT_OPPORTUNITY_NOT_FOUND( "fb.agent.opportunity.not.found", "Agent opportunity not found." ),

    /** The agent task not found. */
    AGENT_TASK_NOT_FOUND( "fb.agent.task.not.found", "Agent task not found." ),

    /** The opportunity contact not found. */
    OPPORTUNITY_CONTACT_NOT_FOUND( "fb.opportunity.contact.not.found", "Opportunity contact not found." ),

    /** User not found. */
    USER_LOGIN_ERROR( "user.login.error", "User not logged-in" ),

    /** Topic handshake failed error. */
    TOPIC_HANDSHAKE_FAILED( "crm.handshake.failed", "Topic subscription handshake failed." ),

    /** The affiliate email validation error. */
    AFFILIATE_EMAIL_VALIDATION_ERROR( "affiliate.email.validation.error", "Affiliate email validatation failed." ),

    /** The affiliate email parsing error. */
    AFFILIATE_EMAIL_PARSING_ERROR( "affiliate.email.parsing.error", "Affiliate email parsing failed." ),

    /** The affiliate email scraping error. */
    AFFILIATE_EMAIL_SCRAPING_ERROR( "affiliate.email.scraping.error", "Affiliate email scraping failed." ),

    /** The note deleted error. */
    NOTE_DELETED_ERROR( "note.deleted.error", "Note deleted error." ),

    /** The note not found error. */
    NOTE_NOT_FOUND_ERROR( "note.not.found.error", "Note not found error." ),

    /** The unsupported notification type. */
    UNSUPPORTED_NOTIFICATION_TYPE( "unsupported.notification.type.error",
            "Unsupported Notification type, push notification cant be sent" ),

    /** The scripted flow call action reminder error. */
    SCRIPTED_FLOW_CALL_ACTION_REMINDER_ERROR( "scripted.flow.call.action.reminder.error",
            "Scripted-flow call action reminder after 10 min error." ),

    /** The lead followup error. */
    LEAD_FOLLOWUP_ERROR( "lead.followup.email.error", "Lead followup processing error." ),

    /** The pdp fetch error. */
    PROPERTY_DETAILS_FETCH_ERROR( "pdp.fetch.error", "Problem getting property details from Owners.com" ),

    /** The missing param error. */
    REQUEST_PARAM_MISSING_ERROR( "error.request.param.required", "Request parameter/s missing" ),

    /** The invalid unbounce lead param error. */
    INVALID_OCL_LEAD_PARAM_ERROR( "error.lead.ocl.data.required", "Invalid buyer ocl lead parameter" ),

    /** The invalid property tour information. */
    INVALID_PROPERTY_TOUR_INFORMATION( "invalid.property.tour.information", "Invalid Property tour information" ),

    FILE_SIGNED_S3_URL_GENERATION_ERROR( "signed.s3.url.generation.error", "Signed s3 url generation error" ),

    /** The invalid task reminder id. */
    INVALID_TASK_REMINDER_ID( "invalid.task.reminder.id", "Invalid task reminder id" ),

    /** The invalid listing id. */
    INVALID_LISTING_ID( "invalid.listing.id", "Invalid listing id" ),

    /** The third party api error. */
    THIRD_PARTY_API_ERROR( "third.party.api.error", "Third party api error" ),

    /** Error in accessing bigquery data. */
    BIGQUERY_DATA_ERROR( "bigquery.data.access.error", "Error in accessing bigquery data" ),

    /** The email id already exists. */
    EMAIL_ID_ALREADY_EXISTS( "email.id.already.exists", "Email id already exists!" ),

    /** The agent not found error. */
    AGENT_RECORD_NOT_FOUND_ERROR( "agent.record.not.found.error", "Agent record not found" ),

    /** The email request required. */
    EMAIL_REQUEST_REQUIRED( "error.request.required", "At least one email request is required" ),

    /** The google service unavailable. */
    GOOGLE_SERVICE_UNAVAILABLE( "error.agent.email.google.service.unavailable", "Google service is unavailable" ),

    /** The feedback link expired. */
    FEEDBACK_LINK_EXPIRED( "feedback.link.expired", "Feedback link has expired" ),

    /** The invalid rating received. */
    INVALID_RATING_RECEIVED( "invalid.rating.received", "Invalid rating received" ),

    /** The invalid rating id received. */
    INVALID_RATING_ID_RECEIVED( "invalid.rating.id.received", "Invalid rating id received" ),

    /** The invalid feedback received. */
    INVALID_FEEDBACK_RECEIVED( "invalid.feedback.received", "Invalid feedback received" ),

    /** The max allowed feedback attempts exceeded. */
    MAX_ALLOWED_FEEDBACK_ATTEMPTS_EXCEEDED( "maximum.allowed.feedback.attempts.exceeded",
            "Maximum allowed feedback attempts exceeded" ),

    /** The group not found. */
    GROUP_NOT_FOUND( "group.not.found", "Group not found" ),

    /** The group name already exists. */
    GROUP_NAME_ALREADY_EXISTS( "duplicate.group.name", "Group name already exists" ),

    /** The group deleted. */
    GROUP_DELETED( "group.archived", "Group is already archived" ),

    /** The userId is invalid. */
    INVALID_USER_ID_PARAMETER( "The userId is invalid",
            "The user id should not have blank spaces and length should be between 1 to 50 characters and should match pattern "
                    + com.owners.gravitas.util.StringUtils.REGEX_ALPA_NUMERIC_UNDERSCORE_HYPEN ),

    /** The user not found. */
    USER_NOT_FOUND( "user.not.found", "User not found" ),

    /** The buyer farming error. */
    BUYER_FARMING_ERROR( "buyer.farming.error", "Buyer farming error" ),

    /** The invalid task status. */
    INVALID_TASK_STATUS( "error.task.status.type.format", "Invalid task status" ),

    /** The location required. */
    LOCATION_REQUIRED( "error.task.location.required", "Location is mandatory when task is check in" ),

    /** The user activity input is invalid. */
    INVALID_USER_ACTIVITY_INPUT_PARAMETER( "error.user.activity.input.format",
            "The email id should not have blank spaces and length should be between 1 to 255 characters and should match pattern "
                    + com.owners.gravitas.util.StringUtils.EMAIL_PATTERN
                    + " and the user id should not have blank spaces and length should be between 1 to 50 characters and should match pattern "
                    + com.owners.gravitas.util.StringUtils.REGEX_ALPA_NUMERIC_UNDERSCORE_HYPEN ),

    /** The invalid opportunity searched. */
    INVALID_OPPORTUNITY_SEARCHED( "invalid.opportunity.searched", "Invalid opportunity searched" ),

    /** The user authentication error. */
    USER_AUTHENTICATION_ERROR( "user.authentication.error", "User authentication failed" ),

    /** opportunity assigment error. */
    OPPORTUNITY_ASSIGMENT_ERROR( "opportunity.assigment.error", "System error. Please retry assignment." ),

    /** opportunity data node key error. */
    INVALID_DATA_NODE_KEY( "opportunity.data.node.error", "System error. Please retry with valid key." ),

    /** opportunity data node key error. */
    NULL_OR_EMPTY_DATA_NODE_KEY( "opportunity.data.node.error", "System error. Please provide node key." ),

    INVALID_REQUEST( "opportunity.data.node.invalid.request", "System error. retry with valid request." ),

    INVALID_PARAMETER( "invalid.parameter", "parameter is invalid" ),

    ATTACHMENT_UPLOAD_FAILURE( "attachments.upload.failed", "The system cannot upload the attachment specified." ),

    INVALID_INPUT( "invalid.inout", "input is invalid" ),

    /** The task delete not allowed created by inside sales. */
    TASK_DELETE_NOT_ALLOWED_CREATED_BY_INSIDE_SALES( "error.task.delete.not.allowed",
            "Cannot delete the task created by inside sales agent" ),

    /** The task delete not allowed for agent when flag is on. */
    TASK_DELETE_NOT_ALLOWED_FOR_AGENT_WHEN_FLAG_IS_ON( "error.task.delete.not.allowed.for.agent",
            "Cannot delete the task by Agent when flag agent.checkin.task.disallow.1099agents.to.delete.tasks is ON." ),

    /** The jmx user not found. */
    JMX_USER_NOT_FOUND( "jmx.user.not.found", "JMX user not found for the provided username" ),

    /** The jmx user already exists. */
    JMX_USER_ALREADY_EXISTS( "jmx.user.already.exists", "JMX user already exists with the given username" ),

    APP_DOWNLOAD_REMINDER_ERROR( "app.download.reminder.error", "app download reminder error" );

    /** error code. */
    private String code;

    /** error detail. */
    private String errorDetail;

    /**
     * Parameterized constructor.
     *
     * @param errorCode
     *            the error code
     * @param errorDetail
     *            the error detail
     */
    private ErrorCode( final String errorCode, final String errorDetail ) {
        this.code = errorCode;
        this.errorDetail = errorDetail;

    }

    /**
     * This method returns the error code.
     *
     * @return error code.
     */
    public String getCode() {
        return code;
    }

    /**
     * This method returns the error detail.
     *
     * @return error detail.
     */
    public String getErrorDetail() {
        return errorDetail;
    }
}
