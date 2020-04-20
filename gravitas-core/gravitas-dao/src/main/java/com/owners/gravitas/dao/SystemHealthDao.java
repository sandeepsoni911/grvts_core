package com.owners.gravitas.dao;

import java.net.URISyntaxException;
import java.util.Set;

import org.springframework.web.client.RestClientException;

/**
 * The Interface SystemHealthDao.
 *
 * @author ankusht
 */
public interface SystemHealthDao {

    /**
     * Connect to ref data node.
     *
     * @return the sets the
     */
    public Set< String > connectToRefDataNode();

    /**
     * Execute query on gravitas DB.
     *
     * @return the int
     */
    public void executeQueryOnGravitasDB();

    /**
     * Gets the rabbit MQ status.
     *
     * @return the rabbit MQ status
     * @throws URISyntaxException
     * @throws RestClientException
     */
    String getRabbitMQStatus() throws RestClientException, URISyntaxException;
}
