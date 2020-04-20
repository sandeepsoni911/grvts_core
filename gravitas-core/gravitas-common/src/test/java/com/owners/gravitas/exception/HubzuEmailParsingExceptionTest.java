package com.owners.gravitas.exception;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * The Class HubzuEmailParsingExceptionTest.
 *
 * @author amits
 */
public class HubzuEmailParsingExceptionTest {

    /**
     * Test affiliate email parsing exception.
     */
    @Test
    public void testHubzuEmailParsingException() {
        final HubzuEmailParsingException exp = new HubzuEmailParsingException( "test", new AffiliateEmailParsingException(
                new MimeMessage( Session.getDefaultInstance( new Properties() ) ), "test", new Exception() ));

        Assert.assertNotNull( exp );
        Assert.assertNotNull( exp.getLocalizedMessage() );
    }
}
