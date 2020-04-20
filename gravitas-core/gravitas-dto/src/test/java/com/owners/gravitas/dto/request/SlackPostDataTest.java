package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class SlackPostDataTest.
 * 
 * @author pabhishek
 */
public class SlackPostDataTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The slack post data values. */
    private PropertiesAndValues slackPostDataValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSlackPostDataTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "token", null );
        defaultValues.put( "team_id", null );
        defaultValues.put( "team_domain", null );
        defaultValues.put( "channel_id", null );
        defaultValues.put( "channel_name", null );
        defaultValues.put( "user_name", null );
        defaultValues.put( "text", null );
        defaultValues.put( "trigger_word", null );
    }

    /**
     * Creates the slack post data test values.
     */
    private void createSlackPostDataTestValues() {
        slackPostDataValues = new PropertiesAndValues();
        slackPostDataValues.put( "token", "testtoken" );
        slackPostDataValues.put( "team_id", "teamid" );
        slackPostDataValues.put( "team_domain", "teamdomain" );
        slackPostDataValues.put( "channel_id", "channelid" );
        slackPostDataValues.put( "channel_name", "channelname" );
        slackPostDataValues.put( "user_name", "username" );
        slackPostDataValues.put( "text", "text" );
        slackPostDataValues.put( "trigger_word", "triggerword" );
    }

    /**
     * Test slack post data.
     */
    @Test
    public final void testSlackPostData() {
        final BeanLikeTester blt = new BeanLikeTester( SlackPostData.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackPostDataValues );
    }
}
