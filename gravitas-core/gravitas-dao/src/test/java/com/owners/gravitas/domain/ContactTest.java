package com.owners.gravitas.domain;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.PhoneNumber;

public class ContactTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * FirebaseAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createContactTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "phoneNumbers", new ArrayList< PhoneNumber >() );
        defaultValues.put( "emails", new ArrayList< String >() );
        defaultValues.put( "preferredContactTime", null );
        defaultValues.put( "preferredContactMethod", null );
        defaultValues.put( "crmId", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "deleted", FALSE );
        defaultValues.put( "ownersId", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createContactTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "firstName", "test" );
        testValues.put( "lastName", "test" );
        testValues.put( "phoneNumbers", new ArrayList< String >() );
        testValues.put( "emails", new ArrayList< String >() );
        testValues.put( "preferredContactTime", "test" );
        testValues.put( "preferredContactMethod", "test" );
        testValues.put( "crmId", "test" );
        testValues.put( "lastModifiedDtm", 0L );
        testValues.put( "lastModifiedBy", null );
        testValues.put( "deleted", TRUE );
        testValues.put( "ownersId", "test" );
    }

    /**
     * Tests the {@link Contact} with default values. Tests the getters and
     * setters of Contact.
     */
    @Test
    public final void testContact() {
        final BeanLikeTester blt = new BeanLikeTester( Contact.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

    @Test
    public final void testEmail() {
        String email = "test@test.com";
        Contact contact = new Contact();
        contact.addEmail( email );
        assertEquals( contact.getEmails().iterator().next(), email );

    }

    @Test
    public final void testPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber();
        Contact contact = new Contact();
        contact.addPhone( phoneNumber );
        assertEquals( contact.getPhoneNumbers().iterator().next(), phoneNumber );

    }
}
