package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;

import java.util.Date;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.GroupBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.GroupOkr;
import com.owners.gravitas.dto.request.UserGroupRequest;

/**
 * The Class GroupBuilderTest.
 *
 * @author raviz
 */
public class GroupBuilderTest extends AbstractBaseMockitoTest {

    /** The group builder. */
    @InjectMocks
    private GroupBuilder groupBuilder;

    /**
     * Test convert to should return group.
     */
    @Test
    public void testConvertToShouldReturnGroup() {
        final UserGroupRequest source = new UserGroupRequest();
        source.setGroupName( "groupName" );
        source.setRelatedOkr( "okr" );
        source.setTestStartDate( new Date() );
        source.setTestEndDate( new Date() );

        final Group destination = groupBuilder.convertTo( source );
        final GroupOkr groupOkr = destination.getGroupOkr();
        assertEquals( destination.getName(), source.getGroupName() );
        assertEquals( groupOkr.getRelatedOkr(), source.getRelatedOkr() );
    }

    /**
     * Test convert to should return group when destination is provided.
     */
    @Test
    public void testConvertToShouldReturnGroupWhenDestinationIsProvided() {
        final UserGroupRequest source = new UserGroupRequest();
        source.setGroupName( "groupName" );
        source.setRelatedOkr( "okr" );
        source.setTestStartDate( new Date() );
        source.setTestEndDate( new Date() );

        final Group destination = groupBuilder.convertTo( source, new Group() );
        final GroupOkr groupOkr = destination.getGroupOkr();
        assertEquals( destination.getName(), source.getGroupName() );
        assertEquals( groupOkr.getRelatedOkr(), source.getRelatedOkr() );
    }

    /**
     * Test convert to should return group when destination is provided with
     * group okr.
     */
    @Test
    public void testConvertToShouldReturnGroupWhenDestinationIsProvidedWithGroupOkr() {
        final UserGroupRequest source = new UserGroupRequest();
        source.setGroupName( "groupName" );
        source.setRelatedOkr( "okr" );
        source.setTestStartDate( new Date() );
        source.setTestEndDate( new Date() );

        final Group group = new Group();
        group.setGroupOkr( new GroupOkr() );
        final Group destination = groupBuilder.convertTo( source, group );
        final GroupOkr groupOkr = destination.getGroupOkr();
        assertEquals( destination.getName(), source.getGroupName() );
        assertEquals( groupOkr.getRelatedOkr(), source.getRelatedOkr() );
    }

    /**
     * Test convert to should return null when source is null.
     */
    @Test
    public void testConvertToShouldReturnNullWhenSourceIsNull() {
        final Group destination = groupBuilder.convertTo( null );
        Assert.assertNull( destination );
    }

    /**
     * Test convert from
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        groupBuilder.convertFrom( new Group() );
    }
}
