package com.owners.gravitas.exception;

import org.flowable.engine.delegate.BpmnError;

import com.owners.gravitas.enums.GravitasProcess;

/**
 * The Class ActivitiException.
 * 
 * @author ankusht
 */
public class ActivitiException extends BpmnError {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8402171210798919528L;

    /** The gravitas process. */
    private final GravitasProcess gravitasProcess;

    /**
     * Instantiates a new activiti exception.
     *
     * @param errorCode
     *            the error code
     * @param message
     *            the message
     * @param gravitasProcess
     *            the gravitas process
     */
    public ActivitiException( final String errorCode, final String message, final GravitasProcess gravitasProcess ) {
        super( errorCode, message );
        this.gravitasProcess = gravitasProcess;
    }

    /**
     * Gets the gravitas process.
     *
     * @return the gravitas process
     */
    public GravitasProcess getGravitasProcess() {
        return gravitasProcess;
    }

}
