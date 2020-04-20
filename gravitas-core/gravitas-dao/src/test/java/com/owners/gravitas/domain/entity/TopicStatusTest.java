package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class TopicStatusTest.
 * 
 * @author pabhishek
 */
public class TopicStatusTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The topic status values. */
    private PropertiesAndValues topicStatusValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTopicStatusTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", "2" );
        defaultValues.put( "topicName", null );
        defaultValues.put( "status", null );
    }

    /**
     * Creates the topic status test values.
     */
    private void createTopicStatusTestValues() {
        topicStatusValues = new PropertiesAndValues();
        topicStatusValues.put( "id", "1" );
        topicStatusValues.put( "topicName", "test" );
        topicStatusValues.put( "status", "active" );
    }

    /**
     * Test topic status.
     */
    @Test
    public final void testTopicStatus() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, String.class );
        mapping.put( signature2, Arrays.asList( "topicName", "status" ) );
        final BeanLikeTester blt = new BeanLikeTester( TopicStatus.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, topicStatusValues );
    }

    /**
     * Test equal and hashcode.
     */
    @Test
    public void testEqualAndHashcode() {
        final TopicStatus topicStatus1 = new TopicStatus();
        topicStatus1.setTopicName( "testtopic" );
        final TopicStatus topicStatus2 = new TopicStatus();
        topicStatus2.setTopicName( "testtopic" );
        Assert.assertEquals( topicStatus1, topicStatus2 );
        Assert.assertEquals( topicStatus1.hashCode(), topicStatus2.hashCode() );
    }

    /**
     * Test hashcode with topic value null.
     */
    @Test
    public void testHashcodeWithTopicValueNull() {
        final TopicStatus topicStatus1 = new TopicStatus();
        topicStatus1.setTopicName( null );
        final TopicStatus topicStatus2 = new TopicStatus();
        topicStatus2.setTopicName( null );
        Assert.assertEquals( topicStatus1.hashCode(), topicStatus2.hashCode() );
    }
}
