package com.owners.gravitas.dto;

/**
 * The Class ProcessManagement.
 *
 * @author amits
 */
public class ProcessDto extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1361153264011956141L;

    /** The crm id. */
    private String crmId;

    /** The email. */
    private String email;

    /** The process code. */
    private String processCode;

    /** The execution id. */
    private String executionId;

    /** The status. */
    private String status;

    /** The gravitas engine id. */
    private String gravitasEngineId;

    /**
     * Gets the crm id.
     *
     * @return the crm id
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * Sets the crm id.
     *
     * @param crmId
     *            the new crm id
     */
    public void setCrmId( final String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * Gets the process code.
     *
     * @return the process code
     */
    public String getProcessCode() {
        return processCode;
    }

    /**
     * Sets the process code.
     *
     * @param processCode
     *            the new process code
     */
    public void setProcessCode( final String processCode ) {
        this.processCode = processCode;
    }

    /**
     * Gets the execution id.
     *
     * @return the execution id
     */
    public String getExecutionId() {
        return executionId;
    }

    /**
     * Sets the execution id.
     *
     * @param executionId
     *            the new execution id
     */
    public void setExecutionId( final String executionId ) {
        this.executionId = executionId;
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
     * Gets the gravitas engine id.
     *
     * @return the gravitas engine id
     */
    public String getGravitasEngineId() {
        return gravitasEngineId;
    }

    /**
     * Sets the gravitas engine id.
     *
     * @param gravitasEngineId
     *            the new gravitas engine id
     */
    public void setGravitasEngineId( final String gravitasEngineId ) {
        this.gravitasEngineId = gravitasEngineId;
    }

}
