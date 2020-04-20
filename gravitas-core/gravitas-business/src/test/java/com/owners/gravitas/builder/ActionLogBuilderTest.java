package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.ActionLogBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.request.ActionRequest;

/**
 * The Class ActionLogBuilderTest.
 *
 * @author vishwanathm
 */
public class ActionLogBuilderTest extends AbstractBaseMockitoTest {

    /** The action log builder. */
    @InjectMocks
    private ActionLogBuilder actionLogBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        ActionRequest request = new ActionRequest();
        request.setActionEntity( "actionOn" );
        request.setActionEntityId( "actionObjectId" );
        request.setActionType( "actionType" );
        request.setCurrentValue( "currentValue" );
        request.setPreviousValue( "previousValue" );
        request.setDescription( "description" );
        request.setPlatform( "platform" );
        request.setPlatformVersion( "platformVersion" );
        ActionLog log = actionLogBuilder.convertTo( request );
        Assert.assertNotNull( log );
    }

    /**
     * Test convert to source as null.
     */
    @Test
    public void testConvertToSourceAsNull() {
        ActionLog log = actionLogBuilder.convertTo( null, new ActionLog() );
        Assert.assertNotNull( log );
    }

    /**
     * Test convert to dest as null.
     */
    @Test
    public void testConvertToDestAsNull() {
        ActionRequest request = new ActionRequest();
        request.setActionEntity( "actionOn" );
        request.setActionEntityId( "actionObjectId" );
        request.setActionType( "actionType" );
        request.setCurrentValue( "currentValue" );
        request.setPreviousValue( "previousValue" );
        request.setDescription( "description" );
        request.setPlatform( "platform" );
        request.setPlatformVersion( "platformVersion" );
        ActionLog log = actionLogBuilder.convertTo( request, null );
        Assert.assertNotNull( log );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        actionLogBuilder.convertFrom( new ActionLog() );
    }
}
