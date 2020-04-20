package com.owners.gravitas.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class OwnersEmailValidationExceptionTest.
 *
 * @author amits
 */
public class OwnersEmailValidationExceptionTest {

    /**
     * Test affiliate email parsing exception.
     */
    @Test
    public void testOwnersEmailValidationException() {
        final OwnersEmailValidationException exp = new OwnersEmailValidationException( "test",
                new AffiliateEmailValidationException(
                        new MimeMessage( Session.getDefaultInstance( new Properties() ) ), "test",
                        new HashMap< String, List< String > >() ) );

        Assert.assertNotNull( exp );
        Assert.assertNotNull( exp.getLocalizedMessage() );
    }
}
