package com.owners.gravitas.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.mockito.InjectMocks;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.SimpleConnection;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.rabbitmq.client.Channel;

public class AmqpCompositeConnectionListenerTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private AmqpCompositeConnectionListener amqpCompositeConnectionListener;

    @Test
    public void testOnCreateShouldSaveAmqpConnectionInAField() {
        Connection actual = new SimpleConnection( null, 10 );
        amqpCompositeConnectionListener.onCreate( actual );
        assertEquals( ( Connection ) ReflectionTestUtils.getField( amqpCompositeConnectionListener, "amqpConnection" ),
                actual );
    }

    @Test
    public void testIsBrokerReachableShouldReturnTrueIfConnectionIsOpen() {
        Connection connection = new Connection() {

            @Override
            public boolean isOpen() {
                return true;
            }

            @Override
            public Channel createChannel( boolean transactional ) throws AmqpException {
                return null;
            }

            @Override
            public void close() throws AmqpException {
            }
        };
        ReflectionTestUtils.setField( amqpCompositeConnectionListener, "amqpConnection", connection );
        boolean isBrokerReachable = amqpCompositeConnectionListener.isBrokerReachable();
        assertTrue( isBrokerReachable );
    }

    @Test
    public void testIsBrokerReachableShouldReturnFalseIfConnectionIsClosed() {
        Connection connection = new Connection() {

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public Channel createChannel( boolean transactional ) throws AmqpException {
                return null;
            }

            @Override
            public void close() throws AmqpException {
            }
        };
        ReflectionTestUtils.setField( amqpCompositeConnectionListener, "amqpConnection", connection );
        boolean isBrokerReachable = amqpCompositeConnectionListener.isBrokerReachable();
        assertFalse( isBrokerReachable );
    }

    @Test( dependsOnMethods = "testIsBrokerReachableShouldReturnFalseIfConnectionIsClosed" )
    public void testIsBrokerReachableShouldReturnFalseIfConnectionIsNull() {
        ReflectionTestUtils.setField( amqpCompositeConnectionListener, "amqpConnection", null );
        boolean isBrokerReachable = amqpCompositeConnectionListener.isBrokerReachable();
        assertFalse( isBrokerReachable );
    }
}
