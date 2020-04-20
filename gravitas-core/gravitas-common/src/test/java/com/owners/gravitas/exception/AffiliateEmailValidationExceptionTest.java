/**
 * 
 */
package com.owners.gravitas.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

/**
 * The Class AffiliateEmailValidationExceptionTest.
 *
 * @author harshads
 */
public class AffiliateEmailValidationExceptionTest {

    /**
     * Test affiliate email parsing exception.
     */
    @Test
    public void testAffiliateEmailValidationException() {
        final AffiliateEmailValidationException exp = new AffiliateEmailValidationException(
                new MimeMessage( Session.getDefaultInstance( new Properties() ) ), "test",
                new HashMap< String, List< String > >() );
        Assert.assertNotNull( exp.getErrorCode().compareTo( ErrorCode.AFFILIATE_EMAIL_VALIDATION_ERROR ) );
        Assert.assertNotNull( exp.getFailedContraints() );
    }
}
