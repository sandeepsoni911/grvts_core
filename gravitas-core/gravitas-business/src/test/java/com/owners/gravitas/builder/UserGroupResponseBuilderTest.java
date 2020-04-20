package com.owners.gravitas.builder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.UserGroupResponseBuilder;
import com.owners.gravitas.business.builder.response.UserDetailsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.GroupOkr;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.response.UserDetailsResponse;
import com.owners.gravitas.dto.response.UserGroupResponse;
import com.owners.gravitas.service.UserGroupService;
import com.owners.gravitas.service.UserService;

/**
 * The Class UserGroupResponseBuilderTest.
 *
 * @author raviz
 */
public class UserGroupResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The user group response builder. */
    @InjectMocks
    private UserGroupResponseBuilder userGroupResponseBuilder;

    /** The user service. */
    @Mock
    private UserService userService;

    /** The user group service. */
    @Mock
    private UserGroupService userGroupService;

    /** The user details response builder. */
    @Mock
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /**
     * Test convert to should return response when source is not null.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenSourceIsNotNull() {
        final List< Group > source = new ArrayList<>();
        final Group group = new Group();
        final GroupOkr groupOkr = new GroupOkr();
        groupOkr.setTestStartDate( new DateTime() );
        groupOkr.setTestEndDate( new DateTime() );
        group.setGroupOkr( groupOkr );
        source.add( group );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup userGroup = new UserGroup();
        userGroup.setGroup( new Group() );
        userGroup.setUser( new User() );
        userGroups.add( userGroup );
        final List< com.google.api.services.admin.directory.model.User > userDetails = new ArrayList<>();
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

        when( userGroupService.findByGroup( Mockito.any( Group.class ) ) ).thenReturn( userGroups );
        when( userService.getUsersByEmails( Mockito.anyListOf( String.class ) ) ).thenReturn( userDetails );
        when( userDetailsResponseBuilder.convertTo( userDetails ) ).thenReturn( userDetailsResponse );

        final UserGroupResponse response = userGroupResponseBuilder.convertTo( source );
        assertNotNull( response );
        assertNotNull( response.getUserGroupDetails() );

        verify( userGroupService ).findByGroup( Mockito.any( Group.class ) );
        verify( userService ).getUsersByEmails( Mockito.anyListOf( String.class ) );
        verify( userDetailsResponseBuilder ).convertTo( userDetails );
    }

    /**
     * Test convert to should return response when source is not null and
     * destination is passed.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenSourceIsNotNullAndDestinationIsPassed() {
        final List< Group > source = new ArrayList<>();
        final Group group = new Group();
        final GroupOkr groupOkr = new GroupOkr();
        groupOkr.setTestStartDate( new DateTime() );
        groupOkr.setTestEndDate( new DateTime() );
        group.setGroupOkr( groupOkr );
        source.add( group );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup userGroup = new UserGroup();
        userGroup.setGroup( new Group() );
        userGroup.setUser( new User() );
        userGroups.add( userGroup );
        final List< com.google.api.services.admin.directory.model.User > userDetails = new ArrayList<>();
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

        when( userGroupService.findByGroup( Mockito.any( Group.class ) ) ).thenReturn( userGroups );
        when( userService.getUsersByEmails( Mockito.anyListOf( String.class ) ) ).thenReturn( userDetails );
        when( userDetailsResponseBuilder.convertTo( userDetails ) ).thenReturn( userDetailsResponse );

        final UserGroupResponse response = userGroupResponseBuilder.convertTo( source, new UserGroupResponse() );
        assertNotNull( response );
        assertNotNull( response.getUserGroupDetails() );

        verify( userGroupService ).findByGroup( Mockito.any( Group.class ) );
        verify( userService ).getUsersByEmails( Mockito.anyListOf( String.class ) );
        verify( userDetailsResponseBuilder ).convertTo( userDetails );
    }

    /**
     * Test convert to should return response when group list is empty.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenGroupListIsEmpty() {
        final List< Group > source = new ArrayList<>();

        final UserGroupResponse response = userGroupResponseBuilder.convertTo( source );
        assertNotNull( response );
        assertNotNull( response.getUserGroupDetails() );

        verifyZeroInteractions( userGroupService );
        verifyZeroInteractions( userService );
        verifyZeroInteractions( userDetailsResponseBuilder );
    }

    /**
     * Test convert to should return response when group dont have agents.
     */
    @Test
    public void testConvertToShouldReturnResponseWhenGroupDontHaveAgents() {
        final List< Group > source = new ArrayList<>();
        final Group group = new Group();
        final GroupOkr groupOkr = new GroupOkr();
        groupOkr.setTestStartDate( new DateTime() );
        groupOkr.setTestEndDate( new DateTime() );
        group.setGroupOkr( groupOkr );
        source.add( group );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup userGroup = new UserGroup();
        userGroup.setGroup( new Group() );
        userGroup.setUser( new User() );
        userGroups.add( userGroup );

        when( userGroupService.findByGroup( Mockito.any( Group.class ) ) ).thenReturn( null );

        final UserGroupResponse response = userGroupResponseBuilder.convertTo( source, new UserGroupResponse() );
        assertNotNull( response );
        assertNotNull( response.getUserGroupDetails() );

        verify( userGroupService ).findByGroup( Mockito.any( Group.class ) );
        verifyZeroInteractions( userService );
        verifyZeroInteractions( userDetailsResponseBuilder );
    }

    /**
     * Test convert to should return null when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final UserGroupResponse response = userGroupResponseBuilder.convertTo( null );
        assertNull( response );
    }

    /**
     * Test convert from
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        userGroupResponseBuilder.convertFrom( new UserGroupResponse() );
    }
}
