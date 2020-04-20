package com.owners.gravitas.builder;

import org.apache.commons.lang3.StringUtils;
import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.RequestSummaryBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class RequestSummaryBuilderTest.
 *
 * @author vishwanathm
 */
public class RequestSummaryBuilderTest extends AbstractBaseMockitoTest {

    /** The request summary builder. */
    @InjectMocks
    private RequestSummaryBuilder requestSummaryBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final String str = "{\"requestType\":\"MAKE_OFFER\"}\n{\"requestType\":\"SCHEDULE_TOUR\",\"propertyTourInformation\":\"propertyTourInformation\"}";
        final String[] requests = str.split( "\n" );
        String strRequest = requestSummaryBuilder.convertTo( requests );
        Assert.assertNotNull( strRequest );

        final String str1 = "\n";
        final String[] requests1 = str1.split( "\n" );
        String strRequest1 = requestSummaryBuilder.convertTo( requests1 );
        Assert.assertNotNull( strRequest1 );
    }

    /**
     * Test convert to with length less then zero.
     */
    @Test
    public void testConvertToWithLengthLessThenZero() {
        final String[] requests = null;
        String strRequest1 = requestSummaryBuilder.convertTo( requests );
        Assert.assertNotNull( strRequest1 );
    }

    /**
     * Test null source.
     */
    @Test
    public void testNullSource() {
        String bldr = requestSummaryBuilder.convertTo( new String[] {} );
        Assert.assertEquals( bldr, StringUtils.EMPTY );
    }
}
