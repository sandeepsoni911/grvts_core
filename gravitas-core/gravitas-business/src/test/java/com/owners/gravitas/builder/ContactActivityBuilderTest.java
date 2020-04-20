package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.AlertDetails;
import com.owners.gravitas.amqp.ClientEventDetails;
import com.owners.gravitas.business.builder.ContactActivityBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ContactActivity;

/**
 * The Class ContactActivityBuilderTest.
 * 
 * @author ankusht
 */
public class ContactActivityBuilderTest extends AbstractBaseMockitoTest {

    /** The contact activity builder. */
    @InjectMocks
    private ContactActivityBuilder contactActivityBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final List< ClientEventDetails > clientEventDetails = new ArrayList<>();
        final ClientEventDetails details = new ClientEventDetails();
        clientEventDetails.add( details );
        final AlertDetails source = new AlertDetails();
        source.setClientEventDetails( clientEventDetails );
        final ContactActivity destination = null;
        final ContactActivity contactActivity = contactActivityBuilder.convertTo( source, destination );
        assertNotNull( contactActivity );
    }

    /**
     * Test convert to when destination is non null.
     */
    @Test
    public void testConvertToWhenDestinationIsNonNull() {
        final List< ClientEventDetails > clientEventDetails = new ArrayList<>();
        final ClientEventDetails details = new ClientEventDetails();
        clientEventDetails.add( details );
        final AlertDetails source = new AlertDetails();
        source.setClientEventDetails( clientEventDetails );
        final ContactActivity destination = new ContactActivity();
        final ContactActivity actual = contactActivityBuilder.convertTo( source, destination );
        assertNotNull( actual );
        assertEquals( destination, actual );
    }

    /**
     * Test convert to when source is non null.
     */
    @Test
    public void testConvertToWhenSourceIsNonNull() {
        final AlertDetails source = null;
        final ContactActivity destination = new ContactActivity();
        final ContactActivity actual = contactActivityBuilder.convertTo( source, destination );
        assertNotNull( actual );
        assertEquals( destination, actual );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        final ContactActivity source = null;
        final AlertDetails destination = null;
        contactActivityBuilder.convertFrom( source, destination );
    }

}
