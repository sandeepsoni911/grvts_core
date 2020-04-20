/**
 *
 */
package com.owners.gravitas.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class SlackAttachment.
 *
 * @author shivamm
 */
public class SlackAttachmentTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues slackAttachmentTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * Address.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSlackAttachmentTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "fallback", null );
        defaultValues.put( "color", null );
        defaultValues.put( "pretext", null );
        defaultValues.put( "authorName", null );
        defaultValues.put( "authorLink", null );
        defaultValues.put( "authorIcon", null );
        defaultValues.put( "title", null );
        defaultValues.put( "titleLink", null );
        defaultValues.put( "text", null );
        defaultValues.put( "imageUrl", null );
        defaultValues.put( "thumbUrl", null );
        defaultValues.put( "slackPostDateTime", 0L );
        defaultValues.put( "fields", new ArrayList< SlackAttachment.Field >() );
    }

    /**
     * This method is to create the actual values.
     */
    private void createSlackAttachmentTestValues() {
        slackAttachmentTestValues = new PropertiesAndValues();
        slackAttachmentTestValues.put( "fallback", "test" );
        slackAttachmentTestValues.put( "color", "test" );
        slackAttachmentTestValues.put( "pretext", "test" );
        slackAttachmentTestValues.put( "authorName", "test" );
        slackAttachmentTestValues.put( "authorLink", "test" );
        slackAttachmentTestValues.put( "authorIcon", "test" );
        slackAttachmentTestValues.put( "title", "test" );
        slackAttachmentTestValues.put( "titleLink", "test" );
        slackAttachmentTestValues.put( "text", "test" );
        slackAttachmentTestValues.put( "imageUrl", "test" );
        slackAttachmentTestValues.put( "thumbUrl", "test" );
        slackAttachmentTestValues.put( "slackPostDateTime", 1L );
        slackAttachmentTestValues.put( "fields", new ArrayList< SlackAttachment.Field >() );

    }

    /**
     * Tests the {@link SlackAttachment} with default values. Tests the getters
     * and setters of SlackAttachment.
     */
    @Test
    public final void testSlackAttachment() {
        final BeanLikeTester blt = new BeanLikeTester( SlackAttachment.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackAttachmentTestValues );
    }

    @Test
    public final void testAddField() {
        SlackAttachment slackAttachment = new SlackAttachment();
        SlackAttachment.Field field = slackAttachment.new Field();
        slackAttachment.addField( field );
        assertEquals( slackAttachment.getFields().iterator().next(), field );
    }

}
