package com.owners.gravitas.dao.impl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.ActExecutionDao;

@Repository
public class ActExecutionDaoImpl implements ActExecutionDao {

	@Autowired
    private EntityManagerFactory entityManagerFactory;
	
	//private String FIND_LATEST_EXECUTIONID = "SELECT ID_ FROM ACT_RU_EXECUTION WHERE PROC_INST_ID_= :procInstanceId ORDER BY START_TIME_ DESC LIMIT 1";
	private String FIND_LATEST_EXECUTION_ID = "SELECT MAX(ID_) FROM ACT_RU_EXECUTION WHERE PROC_INST_ID_= :procInstanceId";
	
	private String FIND_PROCESS_INSTANCE_ID = "SELECT PROC_INST_ID_ FROM ACT_RU_EXECUTION WHERE ID_= :executionId";
	
	@Override
	public String findLatestExecutionId(String procInstanceId) {
		final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( FIND_LATEST_EXECUTION_ID );
        query.setParameter( "procInstanceId", procInstanceId );
        
        return (String)query.getSingleResult();
	}

    @Override
    public String findProcessInstanceId( String executionId ) {
        final Query query = entityManagerFactory.createEntityManager()
                .createNativeQuery( FIND_PROCESS_INSTANCE_ID );
        query.setParameter( "executionId", executionId );
        
        return (String)query.getSingleResult();
    }

}
