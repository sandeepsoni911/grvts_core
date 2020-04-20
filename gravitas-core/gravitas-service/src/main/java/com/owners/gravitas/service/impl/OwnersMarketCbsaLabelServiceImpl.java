package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.OwnersMarketCbsaLabel;
import com.owners.gravitas.repository.OwnersMarketCbsaLabelRepository;
import com.owners.gravitas.service.OwnersMarketCbsaLabelService;

/**
 * The Class OwnersMarketCbsaLabelServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class OwnersMarketCbsaLabelServiceImpl implements OwnersMarketCbsaLabelService {

    /** The owners market cbsa label repository. */
    @Autowired
    private OwnersMarketCbsaLabelRepository ownersMarketCbsaLabelRepository;

    /**
     * Find by zip.
     *
     * @param zip
     *            the zip
     * @return the owners market cbsa label
     */
    @Override
    @Transactional( readOnly = true )
    public OwnersMarketCbsaLabel findByZip( final String zip ) {
        return ownersMarketCbsaLabelRepository.findByZip( zip );
    }

}
