package com.owners.gravitas.builder.response;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.response.RoleDetailsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.dto.response.RoleDetailsResponse;

public class RoleDetailsResponseBuilderTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private RoleDetailsResponseBuilder roleDetailsResponseBuilder;

    /**
     * Test convert to destination is case.
     */
    @Test
    public void testConvertTo() {
        List< Role > source = new ArrayList< Role >();
        Role role1 = new Role();
        role1.setName( "Role1" );
        role1.setDescription( "Description1" );
        Role role2 = new Role();
        role2.setName( "Role2" );
        role2.setDescription( "Description2" );
        Role role3 = new Role();
        role3.setName( "Role3" );
        role3.setDescription( "Description3" );
        source.add( role1 );
        source.add( role2 );
        source.add( role3 );
        RoleDetailsResponse roleDetailsResponse = roleDetailsResponseBuilder.convertTo( source,
                new RoleDetailsResponse() );
        Assert.assertNotNull( roleDetailsResponse );
        Assert.assertEquals( roleDetailsResponse.getRoles().get( 0 ).getName(), source.get( 0 ).getName() );

    }

    /**
     * Test convert to destination is case.
     */
    @Test
    public void testConvertTo_DestinationAsNull() {
        List< Role > source = new ArrayList< Role >();
        Role role1 = new Role();
        role1.setName( "Role1" );
        role1.setDescription( "Description1" );
        Role role2 = new Role();
        role2.setName( "Role2" );
        role2.setDescription( "Description2" );
        Role role3 = new Role();
        role3.setName( "Role3" );
        role3.setDescription( "Description3" );
        source.add( role1 );
        source.add( role2 );
        source.add( role3 );
        RoleDetailsResponse roleDetailsResponse = roleDetailsResponseBuilder.convertTo( source, null );
        Assert.assertNotNull( roleDetailsResponse );
        Assert.assertEquals( roleDetailsResponse.getRoles().get( 0 ).getName(), source.get( 0 ).getName() );

    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        roleDetailsResponseBuilder.convertFrom( new RoleDetailsResponse() );
    }
}
