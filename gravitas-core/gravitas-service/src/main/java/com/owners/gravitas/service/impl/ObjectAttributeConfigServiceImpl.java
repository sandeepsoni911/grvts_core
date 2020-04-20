package com.owners.gravitas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.repository.ObjectAttributeConfigRepository;
import com.owners.gravitas.service.ObjectAttributeConfigService;

/**
 * The Class ObjectAttributeConfigServiceImpl.
 *
 * @author shivamm
 *         The Class ContactServiceImplV1.
 */
@Service
public class ObjectAttributeConfigServiceImpl implements ObjectAttributeConfigService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ObjectAttributeConfigServiceImpl.class );

    /** The object attribute config repository. */
    @Autowired
    private ObjectAttributeConfigRepository objectAttributeConfigRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ObjectAttributeConfigService#
     * findByAttributeNameAndObjectType(java.lang.String,
     * com.owners.gravitas.domain.entity.ObjectType)
     */
    @Override
    public ObjectAttributeConfig getObjectAttributeConfig( String attributeName, ObjectType objectType ) {
        LOGGER.debug( "Get object attribute config object for attribute name " + attributeName );
        return objectAttributeConfigRepository.findByAttributeNameAndObjectType( attributeName, objectType );
    }
}
