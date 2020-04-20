package com.owners.gravitas.listener;

import org.springframework.amqp.rabbit.connection.CompositeConnectionListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.stereotype.Component;


/**
 * The listener interface for receiving amqpCompositeConnection events.
 * The class that is interested in processing a amqpCompositeConnection
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAmqpCompositeConnectionListener<code> method. When
 * the amqpCompositeConnection event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AmqpCompositeConnectionEvent
 * @author ankusht
 */
@Component("amqpCompositeConnectionListener")
public class AmqpCompositeConnectionListener extends CompositeConnectionListener {

    /** The amqp connection. */
    private Connection amqpConnection;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.amqp.rabbit.connection.CompositeConnectionListener#onCreate(org.springframework.amqp.rabbit
     * .connection.Connection)
     */
    public void onCreate(Connection connection) {
        super.onCreate(connection);
        amqpConnection = connection;
    }

    /**
     * Checks if is broker reachable.
     *
     * @return true, if is broker reachable
     */
    public boolean isBrokerReachable() {
        boolean isReachable = false;
        if (null != amqpConnection) {
            isReachable = amqpConnection.isOpen();
        }
        return isReachable;
    }

}