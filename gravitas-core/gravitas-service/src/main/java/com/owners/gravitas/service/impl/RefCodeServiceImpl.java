package com.owners.gravitas.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.domain.entity.RefType;
import com.owners.gravitas.repository.RefCodeRepository;
import com.owners.gravitas.service.RefCodeService;

// TODO: Auto-generated Javadoc
/**
 * The Class RefCodeServiceImpl.
 *
 * @author raviz
 */
@Service
public class RefCodeServiceImpl implements RefCodeService {

    /** The ref code repository. */
    @Autowired
    private RefCodeRepository refCodeRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GroupManagementService#findByCode(java.
     * lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public RefCode findByCode( final String code ) {
        return refCodeRepository.findByCode( code );
    }

    /**
     * Find ref code by ref type.
     *
     * @param refType
     *            the ref type
     * @return the sets the
     */
    @Override
    @Transactional( readOnly = true )
    public Set< RefCode > findRefCodeByRefType( final RefType refType ) {
        return refCodeRepository.findByRefType( refType );
    }

}
