package com.owners.gravitas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.repository.ObjectTypeRepository;
import com.owners.gravitas.service.ObjectTypeService;

/**
 * The Class ObjectTypeServiceImpl.
 *
 * @author shivamm
 */
@Service
public class ObjectTypeServiceImpl implements ObjectTypeService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ObjectTypeServiceImpl.class );

    /** The object attribute config repository. */
    @Autowired
    private ObjectTypeRepository objectTypeRepository;

    @Override
    public ObjectType findByName( String name ) {
        LOGGER.debug( "Fetching object type by name for " + name );
        return objectTypeRepository.findByName( name );
    }
}
