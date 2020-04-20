/**
 *
 */
package com.owners.gravitas.dto.request;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.SlackAttachment;

/**
 * The Class SlackRequestTest.
 *
 * @author harshads
 */
public class SlackRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues slackRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * SlackRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSlackRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "username", null );
        defaultValues.put( "text", null );
        defaultValues.put( "attachments", new ArrayList<>() );

    }

    /**
     * This method is to create the actual values.
     */
    private void createSlackRequestTestValues() {
        slackRequestTestValues = new PropertiesAndValues();
        slackRequestTestValues.put( "username", "123" );
        slackRequestTestValues.put( "text", null );
        slackRequestTestValues.put( "attachments", new ArrayList< SlackAttachment >() );
    }

    /**
     * Tests the {@link SlackRequest} with default values. Tests the getters
     * and setters of SlackRequest.
     */
    @Test
    public final void testLeadRequest() {
        final BeanLikeTester blt = new BeanLikeTester( SlackRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackRequestTestValues );
    }

    /**
     * Test Add attachments method.
     */
    @Test
    public final void testAddAttachments() {
        SlackRequest request = new SlackRequest();
        SlackAttachment slackAttachment = new SlackAttachment();
        request.addAttachments( slackAttachment );
        assertEquals( request.getAttachments().iterator().next(), slackAttachment );

    }

}
