package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.constants.FirebaseQuery.GET_REF_DATA;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createBasicHttpHeader;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import com.owners.gravitas.dao.SystemHealthDao;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class SystemHealthDaoImpl.
 *
 * @author ankusht
 */
@Repository
public class SystemHealthDaoImpl extends BaseDaoImpl implements SystemHealthDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SystemHealthDaoImpl.class );

    /** The jdbc template. */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** Database validation query. */
    @Value( "${db.validationQuery}" )
    private String validationQuery;

    /** The rabbit MQ virtual host. */
    @Value( "${rabbitmq.virtual-host}" )
    private String rabbitMQVirtualHost;

    /** The aliveness test endpoint. */
    @Value( "${rabbitmq.aliveness.test.endpoint}" )
    private String alivenessTestEndpoint;

    @PostConstruct
    public void init() {
        try {
            rabbitMQVirtualHost = URLEncoder.encode( rabbitMQVirtualHost, StandardCharsets.UTF_8.toString() );
        } catch ( Exception e ) {
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.SystemHealthDao#connectToRefDataNode()
     */
    @Override
    public Set< String > connectToRefDataNode() {
        final String reqUrl = getFirebaseHost() + GET_REF_DATA;
        LOGGER.debug( "Connecting to firebase on node: " + reqUrl );
        final Map< String, Boolean > reposne = getRestTemplate().exchange( reqUrl, GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        return reposne.keySet();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.SystemHealthDao#getRabbitMQStatus()
     */
    @Override
    public String getRabbitMQStatus() throws RestClientException, URISyntaxException {
        String status = StringUtils.EMPTY;
        final String reqUrl = alivenessTestEndpoint + rabbitMQVirtualHost;
        LOGGER.debug( "Checking RabbitMQ aliveness for url: " + reqUrl );
        final Map< String, String > resposne = getRestTemplate().exchange( new URI( reqUrl ), GET,
                buildRequest( createBasicHttpHeader( getBasicAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        status = resposne.get( "status" );
        return status;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.SystemHealthDao#executeQueryOnGravitasDB()
     */
    @Override
    public void executeQueryOnGravitasDB() {
        jdbcTemplate.queryForObject( validationQuery, Integer.class );
    }
}
