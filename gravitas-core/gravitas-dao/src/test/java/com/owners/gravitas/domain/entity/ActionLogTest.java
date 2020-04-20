package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ActionLogTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues actionLogValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentInfo.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createActionLogTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "new", true );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );

        defaultValues.put( "actionType", null );
        defaultValues.put( "actionEntity", null );
        defaultValues.put( "actionEntityId", null );
        defaultValues.put( "actionBy", null );
        defaultValues.put( "previousValue", null );
        defaultValues.put( "currentValue", null );
        defaultValues.put( "description", null );
        defaultValues.put( "platform", null );
        defaultValues.put( "platformVersion", null );
        defaultValues.put( "fieldName", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createActionLogTestValues() {
        actionLogValues = new PropertiesAndValues();
        actionLogValues.put( "id", "test" );
        actionLogValues.put( "new", true );

        actionLogValues.put( "id", null );
        actionLogValues.put( "new", true );
        actionLogValues.put( "createdBy", null );
        actionLogValues.put( "createdDate", new DateTime() );
        actionLogValues.put( "lastModifiedBy", null );
        actionLogValues.put( "lastModifiedDate", new DateTime() );

        actionLogValues.put( "actionType", "SMS" );
        actionLogValues.put( "actionEntity", "USER" );
        actionLogValues.put( "actionEntityId", "121212-12121" );
        actionLogValues.put( "actionBy", "test@test.com" );
        actionLogValues.put( "previousValue", "test" );
        actionLogValues.put( "currentValue", "test1" );
        actionLogValues.put( "description", "desc" );
        actionLogValues.put( "platform", "agent-app" );
        actionLogValues.put( "platformVersion", "2.3" );
        actionLogValues.put( "fieldName", "USER" );
    }

    /**
     * Test action log.
     */
    @Test
    public final void testActionLog() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );

        final BeanLikeTester blt = new BeanLikeTester( ActionLog.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, actionLogValues );
    }

}
