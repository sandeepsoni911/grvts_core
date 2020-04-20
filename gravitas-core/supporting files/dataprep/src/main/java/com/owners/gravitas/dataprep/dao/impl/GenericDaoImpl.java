package com.owners.gravitas.dataprep.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dataprep.dao.GenericDao;

/**
 * The Class GenericDaoImpl.
 * 
 * @author ankusht
 */
@Repository
public class GenericDaoImpl implements GenericDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GenericDaoImpl.class );

    /** The Constant CBSA_BY_ZIP. */
    private static final String CBSA_BY_ZIP = "select CBSA FROM me_zipcbsa where ZIP=:zipcode";

    /** The Constant CUBE_REC_BY_EMAIL_AND_ZIP. */
    private static final String CUBE_REC_BY_EMAIL_AND_ZIP = "select * from cube_agent_eligible_zips_v1 where email=:email and zip=:zip";

    /** The Constant VDIM_REC_BY_EMAIL. */
    private static final String VDIM_REC_BY_EMAIL = "select * from v_dim_agent_score where email=:email";

    /** The Constant AGENTS_BY_ZIP. */
    private static final String AGENTS_BY_ZIP = "select dim.EMAIL,dim.AGENT_NAME,dim.HAPPY_AGENTS_SCORE,cube.HOME_ZIP,cube.ZIP,cube.STATE from v_dim_agent_score dim "
            + "inner join cube_agent_eligible_zips_v1 cube on dim.email=cube.email where cube.ZIP=:zip ";

    /** The Constant THRESHOLDS_BY_CBSA. */
    private static final String THRESHOLDS_BY_CBSA = "select * from gr_cbsa_market_level where CBSA_CODE_ID=:cbsa";

    /** The Constant ZIPS_BY_EMAIL. */
    private static final String ZIPS_BY_EMAIL = "select distinct zip from cube_agent_eligible_zips_v1 where EMAIL=:email";

    /** The entity manager factory. */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /** The entity manager. */
    private EntityManager entityManager;

    /**
     * Setup.
     */
    @PostConstruct
    public void setup() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Gets the entity manager.
     *
     * @return the entity manager
     */
    private EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.GenericDao#findCbsaByZip(java.lang.String)
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public String findCbsaByZip( final String zip ) {
        String cbsa = null;
        final Query query = getEntityManager().createNativeQuery( CBSA_BY_ZIP );
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
     * @see com.owners.gravitas.dao.GenericDao#insertData(java.lang.String)
     */
    @Override
    public void insertData( final String queryString ) {
        final EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery( queryString ).executeUpdate();
        entityManager.getTransaction().commit();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.GenericDao#findCubeRecByEmailAndZip(java.lang.
     * String, java.lang.String)
     */
    @Override
    public List< Object[] > findCubeRecByEmailAndZip( final String email, final String zip ) {
        final Query query = getEntityManager().createNativeQuery( CUBE_REC_BY_EMAIL_AND_ZIP );
        query.setParameter( "email", email );
        query.setParameter( "zip", zip );
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.GenericDao#findVdimRecByEmail(java.lang.String)
     */
    @Override
    public List< Object[] > findVdimRecByEmail( final String email ) {
        final Query query = getEntityManager().createNativeQuery( VDIM_REC_BY_EMAIL );
        query.setParameter( "email", email );
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dataprep.dao.GenericDao#getAgentsByZip(java.lang.
     * String)
     */
    @Override
    public List< Object[] > getAgentsByZip( final String zip ) {
        final Query query = getEntityManager().createNativeQuery( AGENTS_BY_ZIP );
        query.setParameter( "zip", zip );
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dataprep.dao.GenericDao#getMarketThresholdsByCbsa(
     * java.lang.String)
     */
    @Override
    public Object[] getMarketThresholdsByCbsa( final String cbsa ) {
        final Query query = getEntityManager().createNativeQuery( THRESHOLDS_BY_CBSA );
        query.setParameter( "cbsa", cbsa );
        return ( Object[] ) query.getSingleResult();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dataprep.dao.GenericDao#findZipsByEmail(java.lang.
     * String)
     */
    @Override
    public List< Object[] > findZipsByEmail( final String email ) {
        final Query query = getEntityManager().createNativeQuery( ZIPS_BY_EMAIL );
        query.setParameter( "email", email );
        return query.getResultList();
    }
}
