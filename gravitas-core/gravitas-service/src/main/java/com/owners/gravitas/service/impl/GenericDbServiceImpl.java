package com.owners.gravitas.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.dao.GenericDao;
import com.owners.gravitas.service.GenericDbService;

/**
 * The Class GenericDbServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class GenericDbServiceImpl implements GenericDbService {

    /** The generic dao. */
    @Autowired
    private GenericDao genericDao;

    /**
     * Find market by zip.
     *
     * @param zip
     *            the zip
     * @return the string
     */
    @Override
    @Transactional( readOnly = true )
    public String findCbsaByZip( final String zip ) {
        return genericDao.findCbsaByZip( zip );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.GenericDbService#executeQuery(java.lang.
     * String, java.util.Map)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Object[] > executeQuery( final String queryStr, final Map< String, String > params ) {
        return genericDao.executeQuery( queryStr, params );
    }
}
