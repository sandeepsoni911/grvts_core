package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.BasicRestAuthenticator;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.RabbitMQAccess;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class SystemHealthDaoImplTest.
 * 
 * @author ankusht
 */
public class SystemHealthDaoImplTest extends AbstractBaseMockitoTest {

    /** The jdbc template. */
    @Mock
    private JdbcTemplate jdbcTemplate;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /** The basic authenticator. */
    @Mock
    private BasicRestAuthenticator basicAuthenticator;

    /** The system health dao impl. */
    @InjectMocks
    private SystemHealthDaoImpl systemHealthDaoImpl;

    /**
     * Test init should encode for valid string.
     */
    @Test
    public void testInitShouldEncodeForValidString() {
        ReflectionTestUtils.setField( systemHealthDaoImpl, "rabbitMQVirtualHost", "/" );
        ReflectionTestUtils.invokeMethod( systemHealthDaoImpl, "init" );
    }

    /**
     * Test init should throw exception for in valid string.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testInitShouldThrowExceptionForInValidString() {
        ReflectionTestUtils.setField( systemHealthDaoImpl, "rabbitMQVirtualHost", null );
        ReflectionTestUtils.invokeMethod( systemHealthDaoImpl, "init" );
    }

    /**
     * Test connect to ref data node should return data fetched from firebase.
     */
    @Test
    public void testConnectToRefDataNodeShouldReturnDataFetchedFromFirebase() {
        Map< String, Boolean > body = new HashMap<>();
        String expected = "key1";
        body.put( expected, null );
        ResponseEntity< Map< String, Boolean > > value = new ResponseEntity< Map< String, Boolean > >( body,
                HttpStatus.OK );
        FirebaseAccess access = new FirebaseAccess();
        when( authenticator.authenticate() ).thenReturn( access );
        when( restTemplate.exchange( anyString(), any(), any(), any( Class.class ) ) ).thenReturn( value );
        Set< String > actual = systemHealthDaoImpl.connectToRefDataNode();
        assertEquals( expected, actual.iterator().next() );
        verify( restTemplate ).exchange( anyString(), any(), any(), any( Class.class ) );
    }

    /**
     * Test get rabbit MQ status should return ok fir aliveness test.
     *
     * @throws RestClientException
     *             the rest client exception
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    @Test
    public void testGetRabbitMQStatusShouldReturnOkFirAlivenessTest() throws RestClientException, URISyntaxException {
        Map< String, String > body = new HashMap<>();
        String expected = "OK";
        body.put( "status", expected );
        ResponseEntity< Map< String, String > > value = new ResponseEntity< Map< String, String > >( body,
                HttpStatus.OK );
        RabbitMQAccess access = new RabbitMQAccess();
        when( basicAuthenticator.authenticate() ).thenReturn( access );
        when( restTemplate.exchange( any( URI.class ), any(), any(), any( Class.class ) ) ).thenReturn( value );
        String actual = systemHealthDaoImpl.getRabbitMQStatus();
        verify( restTemplate ).exchange( any( URI.class ), any(), any(), any( Class.class ) );
        assertEquals( expected, actual );
    }

    /**
     * Test execute query on gravitas DB should execute query on gravitas DB.
     */
    @Test
    public void testExecuteQueryOnGravitasDBShouldExecuteQueryOnGravitasDB() {
        systemHealthDaoImpl.executeQueryOnGravitasDB();
        verify( jdbcTemplate ).queryForObject( anyString(), any( Class.class ) );
    }
}
