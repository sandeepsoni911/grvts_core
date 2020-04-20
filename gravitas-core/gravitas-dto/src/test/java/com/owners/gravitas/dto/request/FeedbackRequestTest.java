package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class FeedbackRequestTest.
 * 
 * @author pabhishek
 */
public class FeedbackRequestTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The feedback request values. */
    private PropertiesAndValues feedbackRequestValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createFeedbackRequestTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "feedback", null );
        defaultValues.put( "comments", null );
        defaultValues.put( "ratingId", null );
        defaultValues.put( "rating", 0 );
    }

    /**
     * Creates the feedback request test values.
     */
    private void createFeedbackRequestTestValues() {
        feedbackRequestValues = new PropertiesAndValues();
        feedbackRequestValues.put( "feedback", "ok" );
        feedbackRequestValues.put( "comments", "comments" );
        feedbackRequestValues.put( "ratingId", "test" );
        feedbackRequestValues.put( "rating", 1 );
    }

    /**
     * Test feedback request.
     */
    @Test
    public final void testFeedbackRequest() {
        final BeanLikeTester blt = new BeanLikeTester( FeedbackRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, feedbackRequestValues );
    }
}
