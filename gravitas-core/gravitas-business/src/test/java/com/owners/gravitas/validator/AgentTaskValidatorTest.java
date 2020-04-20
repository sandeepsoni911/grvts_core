
package com.owners.gravitas.validator;

import static com.owners.gravitas.enums.AgentTaskStatus.CANCELLED;
import static com.owners.gravitas.enums.AgentTaskStatus.COMPLETE;
import static com.owners.gravitas.enums.AgentTaskStatus.COMPLETED;
import static com.owners.gravitas.enums.AgentTaskStatus.DELETED;
import static com.owners.gravitas.enums.AgentTaskStatus.INCOMPLETE;
import static com.owners.gravitas.enums.AgentTaskStatus.INVALID;
import static com.owners.gravitas.enums.AgentTaskStatus.UNASSIGNED;
import static com.owners.gravitas.enums.AgentTaskStatus.VALID;

import org.mockito.InjectMocks;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.enums.AgentTaskStatus;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class AgentTaskValidatorTest.
 *
 * @author raviz
 */
public class AgentTaskValidatorTest extends AbstractBaseMockitoTest {

    /** The agent task validator. */
    @InjectMocks
    private AgentTaskValidator agentTaskValidator;

    /**
     * Test validate status.
     */
    @Test( dataProvider = "status" )
    public void testValidateStatus( final AgentTaskStatus status ) {
        agentTaskValidator.validateStatus( status.name() );
    }

    /**
     * Test validate status when status is blank.
     */
    @Test
    public void testValidateStatusWhenStatusIsBlank() {
        agentTaskValidator.validateStatus( "" );
    }

    /**
     * Test validate status should throw exception when invalid status.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testValidateStatusShouldThrowExceptionWhenInvalidStatus() {
        agentTaskValidator.validateStatus( "wrongTest" );
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    @DataProvider( name = "status" )
    private Object[][] getStatus() {
        return new Object[][] { { COMPLETED }, { INCOMPLETE }, { CANCELLED }, { VALID }, { INVALID }, { UNASSIGNED },
                { DELETED }, { COMPLETE } };
    }

}
