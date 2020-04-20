package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.ErrorCode.JMX_USER_ALREADY_EXISTS;
import static com.owners.gravitas.enums.ErrorCode.JMX_USER_NOT_FOUND;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.JmxUserBusinessService;
import com.owners.gravitas.business.builder.JmxUserBuilder;
import com.owners.gravitas.domain.entity.JmxUser;
import com.owners.gravitas.dto.JmxUserDto;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.JmxUserService;

/**
 * The Class JmxUserBusinessServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class JmxUserBusinessServiceImpl implements JmxUserBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( JmxUserBusinessServiceImpl.class );

    /** The jmx user service. */
    @Autowired
    private JmxUserService jmxUserService;

    /** The jmx user builder. */
    @Autowired
    private JmxUserBuilder jmxUserBuilder;

    /**
     * Adds the jmx user.
     *
     * @param jmxUserDto
     *            the jmx user dto
     */
    @Override
    public void addJmxUser( final JmxUserDto jmxUserDto ) {
        final String username = jmxUserDto.getUsername();
        LOGGER.info( "Adding JMX user " + username );
        final JmxUser dbUser = jmxUserService.findByUsername( username );
        if (dbUser == null) {
            final JmxUser jmxUser = jmxUserBuilder.convertTo( jmxUserDto );
            jmxUserService.save( jmxUser );
            LOGGER.info( "Added JMX user " + username + " in DB successfully" );
        } else {
            throw new ApplicationException( "JMX user already exists with the given username id",
                    JMX_USER_ALREADY_EXISTS );
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.JmxUserBusinessService#delete(java.lang.
     * String)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public void delete( final String username ) {
        LOGGER.info( "Deleting JMX user " + username );
        jmxUserService.delete( username );
        LOGGER.info( "Deleted JMX user " + username );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.JmxUserBusinessService#changePassword(java.
     * lang.String, java.lang.String)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public void changePassword( final String username, final String password ) {
        LOGGER.info( "Changing password for JMX user " + username );
        final JmxUser jmxUser = jmxUserService.findByUsername( username );
        if (jmxUser != null) {
            final String[] encryptedValues = jmxUserService.getEncryptedValues( password );
            jmxUser.setPassword( encryptedValues[0] );
            jmxUser.setIv( encryptedValues[1] );
            jmxUserService.save( jmxUser );
            LOGGER.info( "Changed password for JMX user " + username + " successfully" );
        } else {
            throw new ApplicationException( "JMX user not found for the provided username", JMX_USER_NOT_FOUND );
        }
    }
}
