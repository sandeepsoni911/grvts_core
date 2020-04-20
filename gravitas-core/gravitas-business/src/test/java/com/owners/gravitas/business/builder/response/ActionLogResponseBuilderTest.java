package com.owners.gravitas.business.builder.response;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.response.ActionLogResponseBuilder;
import com.owners.gravitas.business.builder.scraping.hubzu.HomeBidzEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.response.ActionLogResponse;

/**
 * The Class ActionLogResponseBuilderTest.
 *
 * @author shivamm
 */
public class ActionLogResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The message text. */
    final String messageText = "Info: test test test test@test.com Message: test - Property listings by homebidz.co";

    /** The Audit log response builder. */
    @InjectMocks
    private ActionLogResponseBuilder actionLogResponseBuilder;

    /**
     * Test convert to destination is case.
     */
    @Test
    public void testConvertToWhenDestinationIsNull() {
        List< ActionLog > source = new ArrayList<>();
        source.add( new ActionLog() );
        ActionLogResponse actionLogResponse = actionLogResponseBuilder.convertTo( source, null );
        Assert.assertNotNull( actionLogResponse );
    }

    /**
     * Test convert to case.
     */
    @Test
    public void testConvertTo() {
        List< ActionLog > source = new ArrayList<>();
        ActionLogResponse destination = new ActionLogResponse();
        source.add( new ActionLog() );
        ActionLogResponse actionLogResponse = actionLogResponseBuilder.convertTo( source, destination );
        Assert.assertNotNull( actionLogResponse );
    }

    /**
     * Test convert to when source is null case.
     */
    @Test
    public void testConvertToWhenSourceIsNull() {
        List< ActionLog > source = null;
        ActionLogResponse destination = new ActionLogResponse();
        ActionLogResponse actionLogResponse = actionLogResponseBuilder.convertTo( source, destination );
        Assert.assertNotNull( actionLogResponse );
    }

    /**
     * Test convert from.
     */
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testConvertFrom() {
        List< ActionLog > destination = null;
        ActionLogResponse source = new ActionLogResponse();
        actionLogResponseBuilder.convertFrom( source, destination);

    }

}
