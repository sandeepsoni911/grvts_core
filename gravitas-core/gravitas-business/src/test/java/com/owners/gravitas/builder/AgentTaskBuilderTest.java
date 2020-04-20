package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.AgentTaskBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.dto.request.TaskUpdateRequest;

/**
 * The Class AgentTaskBuilderTest.
 *
 * @author raviz
 */
public class AgentTaskBuilderTest extends AbstractBaseMockitoTest {

    /** The agent task builder. */
    @InjectMocks
    private AgentTaskBuilder agentTaskBuilder;

    /**
     * Test convert to should return response when source is not null.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenSourceIsNotNull() {
        final TaskUpdateRequest source = new TaskUpdateRequest();
        final Date dueDtm = new Date();
        final String description = "testDesc";
        final String location = "testLocation";
        final String title = "testTitle";
        source.setDueDtm( dueDtm );
        source.setDescription( description );
        source.setLocation( location );
        source.setTitle( title );

        final AgentTask taskResponse = agentTaskBuilder.convertTo( source );

        assertEquals( taskResponse.getScheduledDtm(), new DateTime( dueDtm ) );
        assertEquals( taskResponse.getDescription(), description );
        assertEquals( taskResponse.getLocation(), location );
        assertEquals( taskResponse.getTitle(), title );

    }

    /**
     * Test convert to when source is not null and agent task passed.
     */
    @Test
    public void testConvertToWhenSourceIsNotNullAndAgentTaskPassed() {
        final TaskUpdateRequest source = new TaskUpdateRequest();
        final String description = "testDesc";
        final String location = "testLocation";
        final String title = "testTitle";
        final AgentTask agentTask = new AgentTask();
        final DateTime scheduleDtm = new DateTime();
        agentTask.setScheduledDtm( scheduleDtm );
        agentTask.setDescription( description );
        agentTask.setLocation( location );
        agentTask.setTitle( title );

        final AgentTask taskResponse = agentTaskBuilder.convertTo( source, agentTask );

        assertEquals( taskResponse.getScheduledDtm(), scheduleDtm );
        assertEquals( taskResponse.getDescription(), description );
        assertEquals( taskResponse.getLocation(), location );
        assertEquals( taskResponse.getTitle(), title );
    }

    /**
     * Test convert to should return null when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final AgentTask taskResponse = agentTaskBuilder.convertTo( null );
        Assert.assertNull( taskResponse );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        final AgentTask source = new AgentTask();
        agentTaskBuilder.convertFrom( source );
    }
}
