package com.owners.gravitas.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimeType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.owners.gravitas.dao.ContactDao;


@Repository
public class ContactDaoImpl implements ContactDao {

	@Autowired
    private EntityManagerFactory entityManagerFactory;
	
	final static String FIND_SAVE_SEARCH_STATUS_BY_CONTACT_ID = "select c.value, c.created_on from gr_contact_json_attr c inner join gr_object_attr_conf o on c.object_attr_conf_id=o.id " 
    		+ "where o.attr_name='farmingSystemActions' and c.contact_id=:contactId";
	
	final static String FIND_ALL_CONTACT_DETAILS_BY_FB_OPP_ID = "select op.fb_opportunity_id,c.first_name,c.last_name from gr_contact c "
			+ "inner join GR_OPPORTUNITY op on c.id = op.contact_id where op.fb_opportunity_id in :fbOpportunityId";
	
	@Override
	public List< Object[] > findSaveSearchStatus( final String contactId ) {
		final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( FIND_SAVE_SEARCH_STATUS_BY_CONTACT_ID );
		query.unwrap(SQLQuery.class).addScalar("value", StringType.INSTANCE);
		query.unwrap(SQLQuery.class).addScalar("created_on", TimestampType.INSTANCE);
        query.setParameter( "contactId", contactId );
        
        return (List< Object[] >)query.getResultList();
	}
	
	@Override
	public List< Object[] > findAllContactsByFbOpportunityId( final Set<String> fbOpportunityId ) {
		final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( FIND_ALL_CONTACT_DETAILS_BY_FB_OPP_ID );
		query.unwrap(SQLQuery.class).addScalar("fb_opportunity_id", StringType.INSTANCE);
		query.unwrap(SQLQuery.class).addScalar("first_name", StringType.INSTANCE);
		query.unwrap(SQLQuery.class).addScalar("last_name", StringType.INSTANCE);
        query.setParameter( "fbOpportunityId", fbOpportunityId );
        
        return (List< Object[] >)query.getResultList();
	}
}
