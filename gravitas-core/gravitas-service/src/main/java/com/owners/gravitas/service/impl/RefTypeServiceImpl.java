package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.RefType;
import com.owners.gravitas.repository.RefTypeRepository;
import com.owners.gravitas.service.RefTypeService;

/**
 * The Class RefTypeServiceImpl.
 * 
 * @author pabhishek
 */
@Service
public class RefTypeServiceImpl implements RefTypeService {

    /** The ref type repository. */
    @Autowired
    private RefTypeRepository refTypeRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.RefTypeService#getRefTypeByType(java.lang.
     * String)
     */
    @Override
    public RefType getRefTypeByType( final String refType ) {
        return refTypeRepository.findByType( refType );
    }
}
