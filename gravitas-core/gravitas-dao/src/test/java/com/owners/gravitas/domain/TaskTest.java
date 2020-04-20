package com.owners.gravitas.domain;

import static com.owners.gravitas.enums.TaskType.REQUEST_INFORMATION;
import static java.lang.Boolean.FALSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.TourDetails;

/**
 * The Class TaskTest.
 *
 * @author amits
 */
public class TaskTest {
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
     * Task.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "title", null );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "taskType", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDtm", null );
        defaultValues.put( "completedDtm", null );
        defaultValues.put( "completedBy", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "requestId", null );
        defaultValues.put( "deleted", FALSE );
        defaultValues.put( "deletedBy", null );
        defaultValues.put( "lastViewedDtm", null );
        defaultValues.put( "description", null );
        defaultValues.put( "dueDtm", null );
        defaultValues.put( "location", null );
        defaultValues.put( "tag", null );
        defaultValues.put( "reminders", null );
        defaultValues.put( "status", null );
        defaultValues.put( "cancellationReason", null );
        defaultValues.put( "isPrimary", FALSE );
        defaultValues.put( "coShoppingId", null );
        defaultValues.put( "dates", null );
        defaultValues.put( "listingId", null );
        defaultValues.put( "isWarmTransferCall", null );

    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "id", "abc" );
        testValues.put( "title", "test" );
        testValues.put( "opportunityId", "test" );
        testValues.put( "taskType", REQUEST_INFORMATION.getType() );
        testValues.put( "createdBy", "test" );
        testValues.put( "createdDtm", 0L );
        testValues.put( "completedDtm", 0L );
        testValues.put( "completedBy", "test" );
        testValues.put( "lastModifiedDtm", 0L );
        testValues.put( "requestId", "test" );
        testValues.put( "deleted", FALSE );
        testValues.put( "deletedBy", "someone" );
        testValues.put( "lastViewedDtm", 0L );
        testValues.put( "description", "test" );
        testValues.put( "dueDtm", 0L );
        testValues.put( "location", "test" );
        testValues.put( "tag", "test" );
        testValues.put( "reminders", new HashMap<>() );
        testValues.put( "status", "test" );
        testValues.put( "cancellationReason", "test" );
        testValues.put( "isPrimary", FALSE );
        testValues.put( "coShoppingId", "teasdasda1as3dst" );
        testValues.put( "isWarmTransferCall", true );
        final List< List< TourDetails > > dates = new ArrayList<>();
        testValues.put( "dates", dates );
        testValues.put( "listingId", "asda" );

    }

    /**
     * Tests the {@link Task} with default values. Tests the getters
     * and setters of Task.
     */
    @Test
    public final void testRequest() {
        final ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        final List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        final List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, String.class, String.class,
                String.class, Long.class );
        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "title", "opportunityId", "taskType", "createdBy", "createdDtm" ) );

        final BeanLikeTester blt = new BeanLikeTester( Task.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
