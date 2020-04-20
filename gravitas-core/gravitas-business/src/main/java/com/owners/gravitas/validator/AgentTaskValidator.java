package com.owners.gravitas.validator;

import static com.owners.gravitas.enums.ErrorCode.INVALID_TASK_STATUS;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.springframework.stereotype.Component;

import com.owners.gravitas.enums.AgentTaskStatus;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class AgentTaskValidator.
 *
 * @author raviz
 */
@Component
public class AgentTaskValidator {

    /**
     * Validate status
     *
     * @param status
     *            the status
     */
    public void validateStatus( final String status ) {
        if (isNotBlank( status )) {
            try {
                AgentTaskStatus.valueOf( status.toUpperCase() );
            } catch ( final IllegalArgumentException e ) {
                throw new ApplicationException( INVALID_TASK_STATUS.getCode(), INVALID_TASK_STATUS );
            }
        }
    }

}
