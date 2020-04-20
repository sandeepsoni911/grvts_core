package com.owners.gravitas.builder;

import java.util.HashMap;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.CRMContactBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;

/**
 * The Class CRMContactBuilderTest.
 *
 * @author amits
 */
public class CRMContactBuilderTest extends AbstractBaseMockitoTest {

    /** The crm lead request builder. */
    @InjectMocks
    private CRMContactBuilder crmContactBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        CRMContactRequest contactRequest = crmContactBuilder.convertTo( new HashMap< String, Object >(),
                new CRMContactRequest() );
        Assert.assertNotNull( contactRequest );
    }

    /**
     * Test convert to source as null.
     */
    @Test
    public void testConvertToSourceAsNull() {
        CRMContactRequest contactRequest = crmContactBuilder.convertTo( null, new CRMContactRequest() );
        Assert.assertNotNull( contactRequest );
    }

    /**
     * Test convert to dest as null.
     */
    @Test
    public void testConvertToDestAsNull() {
        CRMContactRequest contactRequest = crmContactBuilder.convertTo( new HashMap< String, Object >(), null );
        Assert.assertNotNull( contactRequest );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        crmContactBuilder.convertFrom( new CRMContactRequest() );
    }

}
