package com.owners.gravitas.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.CbsaMarketLevel;
import com.owners.gravitas.repository.CbsaMarketLevelRepository;
import com.owners.gravitas.service.CbsaMarketLevelService;

/**
 * The Class CbsaMarketLevelServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class CbsaMarketLevelServiceImpl implements CbsaMarketLevelService {

    /** The cbsa market level repository. */
    @Autowired
    private CbsaMarketLevelRepository cbsaMarketLevelRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.CbsaMarketLevelService#findByCbsaCode(java.
     * lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public CbsaMarketLevel findByCbsaCode( final String cbsaCode ) {
        return cbsaMarketLevelRepository.findByCbsaCode( cbsaCode );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.CbsaMarketLevelService#
     * findThresholdByCbsaCode(java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public int findThresholdByCbsaCode( final String cbsaCode ) {
        return cbsaMarketLevelRepository.findThresholdByCbsaCode( cbsaCode );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.CbsaMarketLevelService#findByCbsaCodes(java.
     * util.Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< CbsaMarketLevel > findByCbsaCodes( final Collection< String > cbsaCodes ) {
        return cbsaMarketLevelRepository.findByCbsaCodes( cbsaCodes );
    }
}
