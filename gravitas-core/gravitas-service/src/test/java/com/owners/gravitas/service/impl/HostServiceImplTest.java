package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class HostServiceImplTest.
 *
 * @author shivamm
 */
public class HostServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent service impl. */
    @InjectMocks
    private HostServiceImpl hostServiceImpl;

    /**
     * Test get host should return current host.
     *
     * @throws UnknownHostException
     *             the unknown host exception
     */
    @Test
    public void testGetHostShouldReturnCurrentHost() throws UnknownHostException {
        String hostName = hostServiceImpl.getHost();
        assertEquals( InetAddress.getLocalHost().getHostName(), hostName );
    }
}
