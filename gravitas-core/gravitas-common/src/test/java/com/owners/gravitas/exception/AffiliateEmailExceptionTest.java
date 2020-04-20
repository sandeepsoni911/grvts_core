package com.owners.gravitas.exception;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

/**
 * The Class AffiliateEmailExceptionTest.
 *
 * @author vishwanathm
 */
public class AffiliateEmailExceptionTest {

    /**
     * Test affiliate email exception.
     */
    @Test
    public void testAffiliateEmailExceptionConstructor1() {
        AffiliateEmailException exp = new AffiliateEmailException(
                new MimeMessage( Session.getDefaultInstance( new Properties() ) ), "test", new Exception() );
        Assert.assertNotNull( exp );
        Assert.assertNotNull( exp.getLocalizedMessage() );
        Assert.assertNotNull( exp.getMailMessage() );
    }

    /**
     * Test affiliate email exception constructor2.
     */
    @Test
    public void testAffiliateEmailExceptionConstructor2() {
        AffiliateEmailException exp = new AffiliateEmailException( ErrorCode.AFFILIATE_EMAIL_PARSING_ERROR,
                new MimeMessage( Session.getDefaultInstance( new Properties() ) ), "test", new Exception() );
        Assert.assertNotNull( exp );
        Assert.assertNotNull( exp.getLocalizedMessage() );
        Assert.assertEquals( exp.getErrorCode(), ErrorCode.AFFILIATE_EMAIL_PARSING_ERROR );
    }
}
