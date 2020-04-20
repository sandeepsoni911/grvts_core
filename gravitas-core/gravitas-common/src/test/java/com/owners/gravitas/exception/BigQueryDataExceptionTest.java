package com.owners.gravitas.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class BigQueryDataExceptionTest.
 *
 * @author shivamm
 */
public class BigQueryDataExceptionTest {

    /**
     * Test big query data exception constructor.
     */
    @Test
    public void testBigQueryDataExceptionConstructor() {
        BigQueryDataException exp = new BigQueryDataException( "test", new Exception() );
        Assert.assertNotNull( exp );
        Assert.assertNotNull( exp.getLocalizedMessage() );
    }

}
