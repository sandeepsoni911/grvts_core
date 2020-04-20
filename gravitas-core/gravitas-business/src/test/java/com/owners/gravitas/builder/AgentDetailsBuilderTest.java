package com.owners.gravitas.builder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.AgentCoverageBuilder;
import com.owners.gravitas.business.builder.AgentDetailsBuilder;
import com.owners.gravitas.business.builder.UserBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.ZipDistance;
import com.owners.gravitas.dto.UserAddress;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.repository.AgentCoverageRepository;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.service.ZipDistanceService;

/**
 * The Class AgentDetailsBuilderTest.
 *
 * @author pabhishek
 */
public class AgentDetailsBuilderTest extends AbstractBaseMockitoTest {

    /** The agent details builder. */
    @InjectMocks
    private AgentDetailsBuilder agentDetailsBuilder;

    /** The agent coverage builder. */
    @Mock
    private AgentCoverageBuilder agentCoverageBuilder;

    /** The user builder. */
    @Mock
    UserBuilder userBuilder;

    /** The user service. */
    @Mock
    UserService userService;

    /** The zip distance service. */
    @Mock
    private ZipDistanceService zipDistanceService;

    /** The agent coverage repository. */
    @Mock
    private AgentCoverageRepository agentCoverageRepository;

    /**
     * Test convert from.
     */
    @Test
    public void testConvertFrom() {
        UserAddress userAddress = new UserAddress();
        userAddress.setZip( "12345" );
        Mockito.when( userService.findByEmail( Mockito.anyString() ) ).thenReturn( new User() );
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setEmail( "test1.user1@gmail.com" );
        request.setRoleId( "test2.user2@gmail.com" );
        request.setMobileCarrier( "atnt" );
        request.setDrivingRadius( "10" );
        request.setAddress( userAddress );
        request.setAgentStartingDate( "10/11/2017" );
        AgentDetails agentDetails = agentDetailsBuilder.convertFrom( request );
        Assert.assertNotNull( agentDetails );
        verify( userService ).findByEmail( Mockito.anyString() );
    }

    /**
     * Test convert from source as null.
     */
    @Test
    public void testConvertFromSourceAsNull() {
        AgentDetails agentDetails = agentDetailsBuilder.convertFrom( null, new AgentDetails() );
        Assert.assertNotNull( agentDetails );
    }

    /**
     * Test convert from dest as null.
     */
    @Test
    public void testConvertFromDestAsNull() {
        UserAddress userAddress = new UserAddress();
        userAddress.setZip( "12345" );
        Mockito.when( userService.findByEmail( Mockito.anyString() ) ).thenReturn( new User() );
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setEmail( "test1.user1@gmail.com" );
        request.setRoleId( "test2.user2@gmail.com" );
        request.setMobileCarrier( "atnt" );
        request.setDrivingRadius( "10" );
        request.setAddress( userAddress );
        request.setAgentStartingDate( "10/11/2017" );
        AgentDetails agentDetails = agentDetailsBuilder.convertFrom( request, null );
        Assert.assertNotNull( agentDetails );
        verify( userService ).findByEmail( Mockito.anyString() );
    }

    /**
     * Test convert from destination as not null.
     */
    @Test
    public void testConvertFromDestinationAsNotNull() {

        UserAddress userAddress = new UserAddress();
        userAddress.setZip( "12345" );
        when( userService.findByEmail( Mockito.anyString() ) ).thenReturn( new User() );
        final AgentDetails details = new AgentDetails();
        details.setHomeZip( "12345" );
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setEmail( "test1.user1@gmail.com" );
        request.setRoleId( "test2.user2@gmail.com" );
        request.setMobileCarrier( "atnt" );
        request.setDrivingRadius( "10" );
        request.setAddress( userAddress );
        request.setAgentStartingDate( "10/11/2017" );

        AgentDetails agentDetails = agentDetailsBuilder.convertFrom( request, details );
        Assert.assertNotNull( agentDetails );
        verify( userService ).findByEmail( Mockito.anyString() );
    }

    /**
     * Test convert from with same driving radius of source and destination.
     */
    @Test
    public void testConvertFromWithSameDrivingRadiusOfSourceAndDestination() {
        UserAddress userAddress = new UserAddress();
        userAddress.setZip( "12345" );
        when( userService.findByEmail( Mockito.anyString() ) ).thenReturn( new User() );
        final AgentDetails details = new AgentDetails();
        details.setHomeZip( "12345" );
        details.setDrivingRadius( 10 );
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setEmail( "test1.user1@gmail.com" );
        request.setRoleId( "test2.user2@gmail.com" );
        request.setMobileCarrier( "atnt" );
        request.setDrivingRadius( "10" );
        request.setAddress( userAddress );
        request.setAgentStartingDate( "10/11/2017" );

        AgentDetails agentDetails = agentDetailsBuilder.convertFrom( request, details );
        Assert.assertNotNull( agentDetails );
        verify( userService ).findByEmail( Mockito.anyString() );
    }

    /**
     * Test convert from with different driving radius of source and
     * destination.
     */
    @Test
    public void testConvertFromWithDifferentDrivingRadiusOfSourceAndDestination() {
        UserAddress userAddress = new UserAddress();
        userAddress.setZip( "12345" );
        when( userService.findByEmail( Mockito.anyString() ) ).thenReturn( new User() );
        final AgentDetails details = new AgentDetails();
        details.setHomeZip( "02345" );
        details.setDrivingRadius( 10 );
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setEmail( "test1.user1@gmail.com" );
        request.setRoleId( "test2.user2@gmail.com" );
        request.setMobileCarrier( "atnt" );
        request.setDrivingRadius( "10" );
        request.setAddress( userAddress );
        request.setAgentStartingDate( "10/11/2017" );
        ZipDistance distance = new ZipDistance();
        distance.setDestinationZip( "12345" );
        final List< ZipDistance > zips = new ArrayList<>();
        zips.add( distance );
        List< String > coverageArea = new ArrayList<>();
        coverageArea.add( "test1" );
        coverageArea.add( "test2" );
        request.setCoverageArea( coverageArea );
        when( zipDistanceService.getZipsWithinCoverageArea( any( Double.class ), anyString() ) ).thenReturn( zips );

        AgentDetails agentDetails = agentDetailsBuilder.convertFrom( request, details );

        Assert.assertNotNull( agentDetails );
        verify( userService ).findByEmail( Mockito.anyString() );
        verify( zipDistanceService ).getZipsWithinCoverageArea( any( Double.class ), anyString() );
    }

    /**
     * Testconvert to.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertTo() {
        agentDetailsBuilder.convertTo( new AgentDetails() );
    }

}
