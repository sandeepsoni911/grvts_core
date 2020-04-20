package com.owners.gravitas.dto.request;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class EmailRequestTest.
 *
 * @author vishwanathm
 */
public class EmailRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues emailRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ReminderRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createEmailRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "to", null );
        defaultValues.put( "cc", null );
        defaultValues.put( "bcc", null );
        defaultValues.put( "subject", null );
        defaultValues.put( "bodyText", null );
        defaultValues.put( "attachmentUrls", null );

    }

    /**
     * This method is to create the actual values.
     */
    private void createEmailRequestTestValues() {
        emailRequestTestValues = new PropertiesAndValues();
        emailRequestTestValues.put( "to", new ArrayList<>() );
        emailRequestTestValues.put( "cc", new ArrayList<>() );
        emailRequestTestValues.put( "bcc", new ArrayList<>() );
        emailRequestTestValues.put( "subject", "test" );
        emailRequestTestValues.put( "bodyText", "test" );
        emailRequestTestValues.put( "attachmentUrls", new ArrayList<>() );

    }

    /**
     * Tests the {@link EmailRequest} with default values. Tests the
     * getters
     * and setters of EmailRequest.
     */
    @Test
    public final void testEmailRequest() {
        final BeanLikeTester blt = new BeanLikeTester( EmailRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, emailRequestTestValues );
    }
}
