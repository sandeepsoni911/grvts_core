package com.owners.gravitas.util;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class PropertiesUtilTest.
 *
 * @author vishwanathm
 */
public class PropertiesUtilTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private PropertiesUtil propertiesUtil;

    @Mock
    private PropertiesUtil propertiesUtilMock;

    /**
     * Test get property for null.
     */
    @Test( priority = 1 )
    public void testGetPropertyForNull() {
        final String obj = PropertiesUtil.getProperty( "test" );
        Assert.assertNull( obj );
    }

    /**
     * Test get properties map.
     */
    @Test( priority = 2 )
    public void testGetPropertiesMap() {
        Map< String, String > obj = PropertiesUtil.getPropertiesMap();
        Assert.assertNotNull( obj );
        obj.put( "test", "test" );

        final String obj1 = PropertiesUtil.getProperty( "test" );
        Assert.assertNotNull( obj1 );
    }

    /**
     * Test set properties map.
     */
    @Test( priority = 1 )
    public void testSetPropertiesMap() {
        final Map< String, String > props = new HashMap< String, String >();
        props.put( "ocl.loan.phase.prospect.stage", "New" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( props );
        }
    }

    /**
     * Test set system properties mode.
     */
    @Test
    public void testSetSystemPropertiesMode() {
        propertiesUtil.setSystemPropertiesMode( 1 );
    }

    @Test
    public void testGetLoanPhasePropertyPrefix() {
        assertNotNull( propertiesUtil.getLoanPhasePropertyPrefix( "test" ) );
    }

    /**
     * Test process properties.
     */
    @Test( priority = 3 )
    public void testProcessProperties() {
        final Properties props = new Properties();
        props.put( "test", "test" );
        propertiesUtil.processProperties( new DefaultListableBeanFactory(), props );
        assertNotNull( PropertiesUtil.getPropertiesMap() );
    }
}
