package com.owners.gravitas.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.GenericDao;

/**
 * The Class GenericDaoImpl.
 */
@Repository
public class GenericDaoImpl implements GenericDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GenericDaoImpl.class );

    /** The Constant CBSA_BY_ZIP. */
    private static final String CBSA_BY_ZIP = "select CBSA FROM me_zipcbsa where ZIP=:zipcode";

    /** The entity manager factory. */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.GenericDao#findCbsaByZip(java.lang.String)
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public String findCbsaByZip( final String zip ) {
        String cbsa = null;
        final Query query = entityManagerFactory.createEntityManager().createNativeQuery( CBSA_BY_ZIP );
        query.setParameter( "zipcode", zip );
        final Object object = query.getResultList();
        if (object != null && object instanceof List) {
            final List< String > cbsaList = ( List< String > ) object;
            if (CollectionUtils.isNotEmpty( cbsaList )) {
                cbsa = cbsaList.get( 0 );
            }
        } else {
            LOGGER.info( "No CBSA is mapped for zip=" + zip );
        }
        return cbsa;
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.GenericDao#executeQuery(java.lang.String,
     * java.util.Map)
     */
    @Override
    public List< Object[] > executeQuery( final String queryStr, final Map< String, String > params ) {
        final Query query = entityManagerFactory.createEntityManager().createNativeQuery( queryStr );
        params.entrySet().forEach( e -> query.setParameter( e.getKey(), e.getValue() ) );
        return query.getResultList();
    }
}
