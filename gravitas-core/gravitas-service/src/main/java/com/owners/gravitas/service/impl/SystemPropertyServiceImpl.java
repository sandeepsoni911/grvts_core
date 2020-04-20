package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.SystemProperty;
import com.owners.gravitas.repository.SystemPropertyRepository;
import com.owners.gravitas.service.SystemPropertyService;

@Service
public class SystemPropertyServiceImpl implements SystemPropertyService {

    /** The system property repository. */
    @Autowired
    private SystemPropertyRepository systemPropertyRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.SystemPropertyService#getProperty(java.lang.
     * String)
     */
    @Override
    public SystemProperty getProperty( final String property ) {
        return systemPropertyRepository.findByName( property );
    }

    /**
     * Gets the properties.
     *
     * @param names
     *            the names
     * @return the properties
     */
    @Override
    public List< SystemProperty > getProperties( final List< String > names ) {
        return systemPropertyRepository.findAllByNameIn( names );
    }
}
