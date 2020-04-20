package com.owners.gravitas.builder;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunityCreate;
import com.owners.gravitas.business.builder.OpportunityContactBuilder;
import com.owners.gravitas.business.builder.OpportunityCreateBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class OpportunityCreateBuilderTest.
 *
 * @author shivamm
 */
public class OpportunityCreateBuilderTest extends AbstractBaseMockitoTest {

    /** The opportunity create builder. */
    @InjectMocks
    private OpportunityCreateBuilder opportunityCreateBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final Map< String, Object > source = new HashMap<>();
        final Map< String, Object > recordType = new HashMap<>();
        recordType.put( "Name", "test" );
        source.put( "RecordType", recordType );
        final OpportunityCreate create = opportunityCreateBuilder.convertTo( source );
        Assert.assertNotNull( create );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        opportunityCreateBuilder.convertFrom( new OpportunityCreate() );
    }
}
