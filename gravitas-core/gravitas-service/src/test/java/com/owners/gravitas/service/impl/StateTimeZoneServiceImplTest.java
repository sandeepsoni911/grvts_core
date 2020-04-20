package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.StateTimeZone;
import com.owners.gravitas.repository.StateTimeZoneRepositiry;

/**
 * The Class StateTimeZoneServiceImplTest.
 *
 * @author vishwanathm
 */
public class StateTimeZoneServiceImplTest extends AbstractBaseMockitoTest {

    /** The state time zone service impl. */
    @InjectMocks
    private StateTimeZoneServiceImpl stateTimeZoneServiceImpl;

    /** The state time zone repositiry. */
    @Mock
    private StateTimeZoneRepositiry stateTimeZoneRepositiry;

    /**
     * Test get state time zone.
     */
    @Test
    public void testGetStateTimeZone() {
        Mockito.when( stateTimeZoneRepositiry.findByStateCode( Mockito.anyString() ) )
                .thenReturn( new StateTimeZone() );
        final StateTimeZone timeZone = stateTimeZoneServiceImpl.getStateTimeZone( "AA" );
        Assert.assertNotNull( timeZone );
    }

}
