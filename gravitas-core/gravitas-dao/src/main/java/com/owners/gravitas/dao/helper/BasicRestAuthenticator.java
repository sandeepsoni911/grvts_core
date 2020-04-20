package com.owners.gravitas.dao.helper;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dto.RabbitMQAccess;

/**
 * The Class BasicRestAuthenticator.
 *
 * @author shivam
 */
@Service( "basicRestAuthenticator" )
public class BasicRestAuthenticator {

    /** The name. */
    @Value( "${rabbitmq.username}" )
    private String name;

    /** The password. */
    @Value( "${rabbitmq.password}" )
    private String password;

    /**
     * Authenticate.
     *
     * @return the rabbit MQ access
     */
    public RabbitMQAccess authenticate() {
        final RabbitMQAccess access = new RabbitMQAccess();
        final String authString = name + ":" + password;
        final byte[] authEncBytes = Base64.encodeBase64( authString.getBytes() );
        final String authStringEnc = new String( authEncBytes );
        access.setAccessToken( authStringEnc );
        return access;
    }
}
